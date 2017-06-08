package arpinum.ddd.event;


import io.vavr.collection.Seq;

@SuppressWarnings("UnusedDeclaration")
public interface EventBus {

    void publish(Seq<Event> events);
}
