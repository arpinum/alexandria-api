package arpinum.infrastructure.bus.event;

import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventBus;
import arpinum.ddd.event.EventBusMiddleware;
import arpinum.ddd.event.EventCaptor;
import arpinum.infrastructure.bus.Computation;
import arpinum.infrastructure.bus.Io;
import arpinum.infrastructure.bus.command.CommandBusAsynchronous;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@SuppressWarnings("UnusedDeclaration")
public class EventBusAsynchronous implements EventBus {

    @Inject
    public EventBusAsynchronous(Set<EventBusMiddleware> middlewares, Set<EventCaptor> captors, @Io ExecutorService executorService) {
        this.executorService = executorService;
        this.captors = List.ofAll(captors);
        middlewareChain = List.ofAll(middlewares).foldRight(new CaptorInvokation(), Chain::new);
    }


    @Override
    public void publish(Seq<Event> events) {
        events.map(e -> Tuple.of(e, captors.filter(c -> c.eventType().equals(e.getClass()))))
                .map(t -> t.apply((e, c) -> c.map(h -> execute(e, h))));
    }

    private Future<Boolean> execute(Event<?> event, EventCaptor<Event<?>> captor) {
        return Future.of(executorService, () -> middlewareChain.apply(captor, event));
    }

    private ExecutorService executorService;
    private final List<EventCaptor> captors;
    private final Chain middlewareChain;

    private final static Logger LOGGER = LoggerFactory.getLogger(EventBusAsynchronous.class);

    private static class Chain {

        Chain(EventBusMiddleware current, Chain next) {
            this.current = current;
            this.next = next;
        }

        public boolean apply(EventCaptor<Event<?>> h, Event<?> event) {
            LOGGER.debug("Running middleware {}", current.getClass());
            current.intercept(event, () -> next.apply(h, event));
            return true;
        }

        private EventBusMiddleware current;
        private Chain next;

    }

    private static class CaptorInvokation extends Chain {

        public CaptorInvokation() {
            super(null, null);
        }

        @Override
        public boolean apply(EventCaptor<Event<?>> h, Event<?> event) {
            LOGGER.debug("Applying captor {}", h.getClass());
            h.execute(event);
            return true;
        }

    }
}
