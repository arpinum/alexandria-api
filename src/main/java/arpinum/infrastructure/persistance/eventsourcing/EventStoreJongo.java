package arpinum.infrastructure.persistance.eventsourcing;

import arpinum.ddd.event.Cursor;
import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventStore;
import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.collection.Stream;
import org.jongo.Jongo;
import org.jongo.MongoCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;


public class EventStoreJongo implements EventStore {

    @Inject
    public EventStoreJongo(@Named("boundedcontext.name") String name, Jongo jongo) {
        this.jongo = jongo;
        this.collection = name + "_eventstore";
    }

    @Override
    public void save(Seq<Event<?>> events) {
        jongo.getCollection(collection).insert(events.toJavaArray());
    }

    @Override
    public <T> Cursor allOf(Class<T> type) {
        MongoCursor<Event> results = jongo.getCollection(collection).find("{targetType:#, _deleted:{$exists:false}}", type.getSimpleName()).as(Event.class);
        return new EventCursor(results);
    }

    @Override
    public <T, E extends Event<T>> Cursor allOfWithType(Class<T> type, Class<E> eventType) {
        MongoCursor<Event> results = jongo
                .getCollection(collection)
                .find("{targetType:#, _deleted:{$exists:false}, _class:#}", type.getSimpleName(), eventType.getName())
                .as(Event.class);
        return new EventCursor(results);
    }

    @Override
    public <T> Cursor allOf(Object id, Class<T> type) {
        MongoCursor<Event> results = jongo.getCollection(collection).find("{targetType:#, targetId:#, _delete:{$exists:false}}", type.getSimpleName(), id).as(Event.class);
        return new EventCursor(results);
    }

    @Override
    public <T> long count(Object id, Class<T> type) {
        return jongo.getCollection(collection).count("{targetType:#, targetId:#}", type.getSimpleName(), id);
    }

    @Override
    public void markAllAsDeleted(Object id, Class<?> type) {
        jongo.getCollection(collection).update("{targetId:#, targetType:#}", id, type.getSimpleName()).multi().with("{$set:{_deleted:true}}");
    }

    private void closeQuietly(MongoCursor<Event> results) {
        try {
            results.close();
        } catch (IOException e) {
            LOGGER.error("Error loading events", e);
        }
    }

    private final String collection;

    private Jongo jongo;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventStoreJongo.class);

    private class EventCursor implements Cursor {

        private final MongoCursor<Event> results;

        public EventCursor(MongoCursor<Event> results) {
            this.results = results;
        }

        @Override
        public long count() {
            return results.count();
        }

        @Override
        public <T> T consume(Function1<Stream<Event>, T> consumer) {
            return consumer
                    .andThen(r -> {
                        closeQuietly(results);
                        return r;
                    })
                    .apply(Stream.ofAll(results));
        }
    }
}
