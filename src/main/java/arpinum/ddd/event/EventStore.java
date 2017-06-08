package arpinum.ddd.event;


import io.vavr.collection.Seq;

public interface EventStore {

    void save(Seq<Event<?>> events);

    <T> Cursor allOf(Class<T> type);

    <T, E extends Event<T>> Cursor allOfWithType(Class<T> type, Class<E> eventType);

    <T> Cursor allOf(Object id, Class<T> type);

    <T> long count(Object id, Class<T> type);

    void markAllAsDeleted(Object id, Class<?> type);
}
