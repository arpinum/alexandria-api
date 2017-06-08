package arpinum.saga;


import arpinum.command.Command;
import arpinum.command.CommandBus;
import arpinum.command.CommandMiddleware;
import arpinum.ddd.event.Event;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.Set;
import java.util.function.Supplier;

public class SagaMiddleware implements CommandMiddleware {

    @Inject
    public SagaMiddleware(Set<SagaHandler> handlers) {
        this.handlers = Vector.ofAll(handlers)
                .toMap(e -> Tuple.of(e.sagaType(), e));
    }

    @Override
    public <T> Future<Tuple2<T, Seq<Event>>> intercept(CommandBus bus, Command<T> message, Supplier<Future<Tuple2<T, Seq<Event>>>> next) {
        return handlers.get(message.getClass())
                .map(h -> h.run(bus, (Saga<T>) message)
                        .map(r -> Tuple.of(r, (Seq<Event>) Vector.<Event>empty())))
                .getOrElse(() -> next.get());

    }

    private final Map<Class, SagaHandler> handlers;
}
