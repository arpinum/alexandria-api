package arpinum.infrastructure.bus.event;


import arpinum.command.Command;
import arpinum.command.CommandBus;
import arpinum.command.CommandMiddleware;
import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventBus;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.function.Supplier;

public class EventDispatcherMiddleware implements CommandMiddleware {

    @Inject
    public EventDispatcherMiddleware(EventBus bus) {
        this.eventBus = bus;
    }


    @Override
    public <T> Future<Tuple2<T, Seq<Event>>> intercept(CommandBus bus, Command<T> message, Supplier<Future<Tuple2<T, Seq<Event>>>> next) {
        return next.get()
                .onSuccess(t -> eventBus.publish(t.apply((r, e)->e)));
    }

    private EventBus eventBus;
}
