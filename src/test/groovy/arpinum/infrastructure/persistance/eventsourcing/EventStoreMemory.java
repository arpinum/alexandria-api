package arpinum.infrastructure.persistance.eventsourcing;

import arpinum.ddd.event.Cursor;
import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventStore;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;


public class EventStoreMemory implements EventStore {

    @Override
    public void save(Seq<Event<?>> events) {
        this.events = this.events.appendAll(events);
    }

    @Override
    public <T> Cursor allOf(Class<T> type) {
        return new MemoryCursor(events.filter(e -> e.targetType().equals(type)));
    }

    @Override
    public <T, E extends Event<T>> Cursor allOfWithType(Class<T> type, Class<E> eventType) {
        return new MemoryCursor(events.filter(e -> e.targetType().equals(type) && e.getClass().equals(eventType)));

    }

    @Override
    public <T> Cursor allOf(Object id, Class<T> type) {
        return new MemoryCursor(events.filter(e -> e.targetType().equals(type) && e.getTargetId().equals(id)));
    }

    @Override
    public <T> long count(Object id, Class<T> type) {
        return allOf(id, type).count();
    }


    @Override
    public void markAllAsDeleted(Object id, Class<?> type) {
        events = events.filter(e -> !id.equals(e.getTargetId()));
    }


    private List<Event> events = List.empty();

    private static class MemoryCursor implements Cursor {
        private final List<Event> filter;

        public MemoryCursor(List<Event> filter) {
            this.filter = filter;
        }

        @Override
        public long count() {
            return filter.size();
        }

        @Override
        public <T> T consume(Function1<Stream<Event>, T> consumer) {
            return consumer.apply(filter.toStream());
        }
    }
}
