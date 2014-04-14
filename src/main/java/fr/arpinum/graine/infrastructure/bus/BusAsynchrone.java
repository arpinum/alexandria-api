package fr.arpinum.graine.infrastructure.bus;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BusAsynchrone implements Bus {

    public BusAsynchrone(Set<? extends SynchronisationBus> synchronisations, Set<? extends HandlerMessage> handlers) {
        for (HandlerMessage handler : handlers) {
            this.handlers.put(handler.typeCommande(), handler);
        }
        this.synchronisations.addAll(synchronisations);
    }

    @Override
    public <TReponse> ResultatExecution<TReponse> posteToutDeSuite(Message<TReponse> message) {
        return Futures.getUnchecked(poste(message));
    }

    @Override
    public <TReponse> ListenableFuture<ResultatExecution<TReponse>> poste(Message<TReponse> message) {
        final HandlerMessage handlerMessage = handlers.get(message.getClass());
        if (handlerMessage == null) {
            LOGGER.warn("Impossible de trouver un handler pour {}", message.getClass());
            return Futures.immediateFuture(ResultatExecution.erreur(new ErreurBus("Impossible de trouver un handler")));
        }
        return executorService.submit(execute(message, handlerMessage));
    }

    private <TReponse> Callable<ResultatExecution<TReponse>> execute(Message<TReponse> message, HandlerMessage<Message<TReponse>, TReponse> handlerMessage) {
        return () -> {
            try {

                synchronisations.forEach((synchro) -> synchro.avantExecution(message));
                final TReponse reponse = handlerMessage.execute(message);
                synchronisations.forEach(SynchronisationBus::apresExecution);
                return ResultatExecution.succes(reponse);
            } catch (Throwable e) {
                synchronisations.forEach(SynchronisationBus::apresExecution);
                LOGGER.error("Erreur sur message {} : {}", message, e.getMessage());
                return ResultatExecution.erreur(e);
            } finally {
                synchronisations.forEach(SynchronisationBus::finalement);
            }
        };
    }

    public void setExecutor(ExecutorService executor) {
        this.executorService = MoreExecutors.listeningDecorator(executor);
    }

    private final Set<SynchronisationBus> synchronisations = Sets.newHashSet();
    private final Map<Class<?>, HandlerMessage> handlers = Maps.newConcurrentMap();
    private ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            Executors.newCachedThreadPool(
                    new ThreadFactoryBuilder().setNameFormat(getClass().getSimpleName() + "-%d").build())
    );

    private final static Logger LOGGER = LoggerFactory.getLogger(BusAsynchrone.class);
}
