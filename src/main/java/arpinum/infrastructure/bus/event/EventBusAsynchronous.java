package arpinum.infrastructure.bus.event;

import arpinum.ddd.event.*;
import arpinum.infrastructure.bus.Io;
import io.vavr.collection.*;
import io.vavr.concurrent.Future;
import org.slf4j.*;

import javax.inject.Inject;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@SuppressWarnings("UnusedDeclaration")
public class EventBusAsynchronous implements EventBus {

    @Inject
    public EventBusAsynchronous(Set<EventBusMiddleware> middlewares, Set<EventCaptor> captors, @Io ExecutorService executorService) {
        this.executorService = executorService;
        middlewareChain = List.ofAll(middlewares)
                .foldRight(new CaptorInvokation(List.ofAll(captors)), Chain::new);
    }


    @Override
    public void publish(Seq<Event> events) {
        events.map(this::execute);
    }

    private Future<Boolean> execute(Event<?> event) {
        return Future.of(executorService, () -> middlewareChain.apply(event));
    }

    private ExecutorService executorService;
    private final Chain middlewareChain;

    private final static Logger LOGGER = LoggerFactory.getLogger(EventBusAsynchronous.class);

    private static class Chain {

        Chain(EventBusMiddleware current, Chain next) {
            this.current = current;
            this.next = next;
        }

        public boolean apply(Event<?> event) {
            LOGGER.debug("Running middleware {}", current.getClass());
            current.intercept(event, () -> next.apply(event));
            return true;
        }

        private EventBusMiddleware current;
        private Chain next;

    }

    private static class CaptorInvokation extends Chain {

        public <T> CaptorInvokation(List<EventCaptor> captors) {
            super(null, null);
            this.captors = captors;
        }

        @Override
        public boolean apply(Event<?> event) {
            return captors.filter(c -> c.eventType().equals(event.getClass()))
                    .map(h -> {
                        LOGGER.debug("Applying captor {}", h.getClass());
                        h.execute(event);
                        return true;
                    })
                    .reduce((a, b) -> a && b);
        }

        private List<EventCaptor> captors;
    }
}
