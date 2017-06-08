package arpinum.command;


import arpinum.ddd.event.Event;
import arpinum.infrastructure.Nothing;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;

public interface VoidCommandHandler<TCommand extends VoidCommand> extends CommandHandler<TCommand, Nothing> {


    @Override
    default Tuple2<Nothing, Seq<Event>> execute(TCommand tCommand) {
        return Tuple.of(null, doExecute(tCommand));
    }

    Seq<Event> doExecute(TCommand command);

}
