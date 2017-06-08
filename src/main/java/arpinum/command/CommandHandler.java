package arpinum.command;


import arpinum.ddd.event.Event;
import com.google.common.reflect.TypeToken;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;

public interface CommandHandler<TCommand extends Command<TResponse>, TResponse> {

    Tuple2<TResponse, Seq<Event>> execute(TCommand tCommand);

    default Class<TCommand> commandType() {
        return (Class<TCommand>) new TypeToken<TCommand>(getClass()) {
        }.getRawType();
    }
}
