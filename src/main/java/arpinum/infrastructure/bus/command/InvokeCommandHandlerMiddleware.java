package arpinum.infrastructure.bus.command;


import arpinum.command.Command;
import arpinum.command.CommandBus;
import arpinum.command.CommandHandler;
import arpinum.command.CommandMiddleware;
import arpinum.ddd.event.Event;
import arpinum.infrastructure.bus.CaptorNotFound;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public class InvokeCommandHandlerMiddleware implements CommandMiddleware {

    public InvokeCommandHandlerMiddleware(Set<CommandHandler> handlers, ExecutorService executorService) {
        this.executorService = executorService;
        this.handlers = Vector.ofAll(handlers).toMap(h -> Tuple.of(h.commandType(), h));
    }

    @Override
    public <T> Future<Tuple2<T, Seq<Event>>> intercept(CommandBus bus, Command<T> message, Supplier<Future<Tuple2<T, Seq<Event>>>> next) {
        return handlers.get(message.getClass())
                .map(h -> {
                    LOGGER.debug("Applying handler {}", h.getClass());
                    return Future.ofCallable(executorService, () -> (Tuple2<T, Seq<Event>>) h.execute(message));
                }).getOrElseThrow(() -> new CaptorNotFound(message.getClass()));

    }

    private final Map<Class, CommandHandler> handlers;
    private ExecutorService executorService;
    private final static Logger LOGGER = LoggerFactory.getLogger(CommandBusAsynchronous.class);
}
