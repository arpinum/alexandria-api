package arpinum.ddd.event;


import io.vavr.Function1;
import io.vavr.collection.Stream;

public interface Cursor {

    long count();

    <T> T consume(Function1<Stream<Event>, T> consumer);
}
