package arpinum.infrastructure.bus.query;


import arpinum.infrastructure.bus.CaptorNotFound;
import arpinum.infrastructure.bus.Io;
import arpinum.query.Query;
import arpinum.query.QueryBus;
import arpinum.query.QueryHandler;
import arpinum.query.QueryMiddleware;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class QueryBusAsynchronous implements QueryBus {

    @Inject
    public QueryBusAsynchronous(Set<QueryMiddleware> middlewares, Set<QueryHandler> handlers, @Io ExecutorService executor) {
        this.executor = executor;
        middlewareChain = List.ofAll(middlewares)
                .foldRight(new HandlerInvokation(List.ofAll(handlers)), Chain::new);
    }

    @Override
    public <TResponse> Future<TResponse> send(Query<TResponse> query) {
        return Future.of(executor, ()->middlewareChain.apply(query));
    }

    private final Chain middlewareChain;
    private final ExecutorService executor;
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBusAsynchronous.class);

    private static class Chain {

        Chain(QueryMiddleware current, Chain next) {
            this.current = current;
            this.next = next;
        }

        public <T> T apply(Query<T> command) {
            LOGGER.debug("Running middleware {}", current.getClass());
            return current.intercept(command, () -> next.apply(command));
        }

        private QueryMiddleware current;
        private Chain next;
    }

    private static class HandlerInvokation extends Chain {

        public HandlerInvokation(List<QueryHandler> handlers) {
            super(null, null);
            this.handlers = handlers;
        }

        @Override
        public <T> T apply(Query<T> command) {
            return handlers.filter(h->h.queryType().equals(command.getClass()))
                    .map(h -> {
                        LOGGER.debug("Applying handler {}", h.getClass());
                        return h.execute(command);
                    })
                    .map(o -> (T) o)
                    .peekOption()
                    .getOrElseThrow(()->new CaptorNotFound(command.getClass()));
        }

        private List<QueryHandler> handlers;
    }
}
