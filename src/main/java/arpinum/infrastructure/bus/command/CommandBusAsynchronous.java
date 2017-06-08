package arpinum.infrastructure.bus.command;

import arpinum.command.Command;
import arpinum.command.CommandBus;
import arpinum.command.CommandHandler;
import arpinum.command.CommandMiddleware;
import arpinum.ddd.event.Event;
import arpinum.infrastructure.bus.Computation;
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
public class CommandBusAsynchronous implements CommandBus {

    @Inject
    public CommandBusAsynchronous(Set<CommandMiddleware> middlewares, Set<CommandHandler> handlers, @Computation ExecutorService executor) {
        middlewareChain = List.ofAll(middlewares)
                .append(new InvokeCommandHandlerMiddleware(handlers, executor))
                .foldRight(new EmptyChain(), Chain::new);
    }

    @Override
    public <TReponse> Future<TReponse> send(Command<TReponse> message) {
        return middlewareChain.apply(message)
                .map(t -> t.apply((r, e) -> r));
    }


    private final Chain middlewareChain;
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandBusAsynchronous.class);

    private class Chain {

        Chain(CommandMiddleware current, Chain next) {
            this.current = current;
            this.next = next;
        }

        public <T> Future<Tuple2<T, Seq<Event>>> apply(Command<T> command) {
            LOGGER.debug("Running middleware {}", current.getClass());

            return current.intercept(CommandBusAsynchronous.this, command, () -> next.apply(command));
        }

        private CommandMiddleware current;
        private Chain next;
    }

    private class EmptyChain extends Chain {
        public EmptyChain() {
            super(null, null);
        }

        @Override
        public <T> Future<Tuple2<T, Seq<Event>>> apply(Command<T> command) {
            throw new RuntimeException("Can't happen");
        }
    }
}


