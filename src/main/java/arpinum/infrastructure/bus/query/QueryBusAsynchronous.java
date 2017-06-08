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
        this.handlers = List.ofAll(handlers);
        middlewareChain = List.ofAll(middlewares).foldRight(new HandlerInvokation(), Chain::new);
    }

    @Override
    public <TResponse> Future<TResponse> send(Query<TResponse> query) {
        return handlers.find(h -> h.queryType().equals(query.getClass()))
                .map(h -> (QueryHandler<Query<TResponse>, TResponse>) h)
                .map(h -> execute(h, query))
                .getOrElse(() -> Future.failed(new CaptorNotFound(query.getClass())));
    }

    private <TReponse> Future<TReponse> execute(QueryHandler<Query<TReponse>, TReponse> handler, Query<TReponse> command) {
        return Future.of(executor, () -> middlewareChain
                .apply(handler, command));
    }

    private final List<QueryHandler> handlers;
    private final Chain middlewareChain;
    private final ExecutorService executor;
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryBusAsynchronous.class);

    private static class Chain {

        Chain(QueryMiddleware current, Chain next) {
            this.current = current;
            this.next = next;
        }

        public <T> T apply(QueryHandler<Query<T>, T> h, Query<T> command) {
            LOGGER.debug("Running middleware {}", current.getClass());
            return current.intercept(command, () -> next.apply(h, command));
        }

        private QueryMiddleware current;
        private Chain next;
    }

    private static class HandlerInvokation extends Chain {
        public HandlerInvokation() {
            super(null, null);
        }

        @Override
        public <T> T apply(QueryHandler<Query<T>, T> h, Query<T> command) {
            LOGGER.debug("Applying handler {}", h.getClass());
            return h.execute(command);
        }
    }
}
