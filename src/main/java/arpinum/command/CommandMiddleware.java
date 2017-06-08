package arpinum.command;


import arpinum.ddd.event.Event;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;

import java.util.function.Supplier;

public interface CommandMiddleware {

    <T> Future<Tuple2<T, Seq<Event>>> intercept(CommandBus bus, Command<T> message, Supplier<Future<Tuple2<T, Seq<Event>>>> next);
}
