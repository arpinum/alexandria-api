package fr.arpinum.graine.infrastructure.bus;

import com.google.common.collect.*;
import com.google.common.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;

public abstract class BusAsynchrone implements Bus {

    public BusAsynchrone(Set<? extends SynchronisationBus> synchronisations, Set<? extends CapteurMessage> handlers) {
        for (CapteurMessage handler : handlers) {
            this.handlers.put(handler.typeCommande(), handler);
        }
        this.synchronisations.addAll(synchronisations);
    }

    @Override
    public <TReponse> ResultatExecution<TReponse> envoieEtAttendReponse(Message<TReponse> message) {
        return Futures.getUnchecked(envoie(message));
    }

    @Override
    public <TReponse> ListenableFuture<ResultatExecution<TReponse>> envoie(Message<TReponse> message) {
        final Collection<CapteurMessage> capteurs = handlers.get(message.getClass());
        if (capteurs.size() == 0) {
            LOGGER.warn("Impossible de trouver un handler pour {}", message.getClass());
            return Futures.immediateFuture(ResultatExecution.erreur(new ErreurBus("Impossible de trouver un handler")));
        }
        LOGGER.debug("Exécution capteur pour {}", message.getClass());
        List<ListenableFuture<ResultatExecution<TReponse>>> futures = Lists.newArrayList();
        capteurs.forEach(c ->
                        futures.add(executorService.submit(execute(message, c)))
        );
        return futures.get(0);
    }

    private <TReponse> Callable<ResultatExecution<TReponse>> execute(Message<TReponse> message, CapteurMessage<Message<TReponse>, TReponse> capteurMessage) {
        return () -> {
            try {
                synchronisations.forEach((synchro) -> synchro.avantExecution(message));
                final TReponse reponse = capteurMessage.execute(message);
                synchronisations.forEach(SynchronisationBus::apresExecution);
                return ResultatExecution.succes(reponse);
            } catch (Throwable e) {
                synchronisations.forEach(SynchronisationBus::surErreur);
                LOGGER.error("Erreur sur message", e);
                return ResultatExecution.erreur(e);
            } finally {
                synchronisations.forEach(SynchronisationBus::finalement);
            }
        };
    }

    public void setExecutor(ExecutorService executor) {
        this.executorService = MoreExecutors.listeningDecorator(executor);
    }

    private final List<SynchronisationBus> synchronisations = Lists.newArrayList();
    private final Multimap<Class<?>, CapteurMessage> handlers = ArrayListMultimap.create();
    private ListeningExecutorService executorService = MoreExecutors.listeningDecorator(
            Executors.newCachedThreadPool(
                    new ThreadFactoryBuilder().setNameFormat(getClass().getSimpleName() + "-%d").build())
    );

    protected final static Logger LOGGER = LoggerFactory.getLogger(BusAsynchrone.class);
}