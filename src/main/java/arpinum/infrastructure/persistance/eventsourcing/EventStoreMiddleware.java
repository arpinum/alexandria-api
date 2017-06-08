package arpinum.infrastructure.persistance.eventsourcing;

import arpinum.command.Command;
import arpinum.command.CommandBus;
import arpinum.command.CommandMiddleware;
import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventStore;
import arpinum.infrastructure.bus.Io;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;


public class EventStoreMiddleware implements CommandMiddleware {

    @Inject
    public EventStoreMiddleware(EventStore eventStore, @Io ExecutorService executor) {
        this.eventStore = eventStore;
        this.executor = executor;
    }

    @Override
    public <T> Future<Tuple2<T, Seq<Event>>> intercept(CommandBus bus, Command<T> message, Supplier<Future<Tuple2<T, Seq<Event>>>> next) {
        return next.get()
                .flatMap(result-> Future.ofCallable(executor, () -> {
                    eventStore.save(result.apply((r, e) -> e).map(e -> (Event) e));
                    return result;
                }));
    }

    private final EventStore eventStore;
    private ExecutorService executor;
}
