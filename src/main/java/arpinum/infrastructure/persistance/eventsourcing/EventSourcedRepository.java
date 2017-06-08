package arpinum.infrastructure.persistance.eventsourcing;

import arpinum.ddd.BaseAggregate;
import arpinum.ddd.Repository;
import arpinum.ddd.event.Cursor;
import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventSourceHandler;
import arpinum.ddd.event.EventStore;
import com.google.common.reflect.TypeToken;
import io.vavr.Tuple;
import io.vavr.collection.Map;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventSourcedRepository<TId, TRoot extends BaseAggregate<TId>> implements Repository<TId, TRoot> {

    public EventSourcedRepository(EventStore eventStore) {
        this.eventStore = eventStore;
        handlers = Vector.of(typeToken.getRawType().getMethods())
                .filter(m -> m.getAnnotationsByType(EventSourceHandler.class).length > 0)
                .toMap(m -> Tuple.of(m.getParameters()[0].getType(), m));
        if (handlers.size() == 0) {
            LoggerFactory.getLogger(EventSourcedRepository.class).warn("No event handlers on {}", typeToken);
        }
    }

    @Override
    public Option<TRoot> get(TId tId) {
        return load(tId);
    }

    private Option<TRoot> load(TId tId) {
        Cursor cursor = eventStore.allOf(tId, type());
        if (cursor.count() == 0) {
            return Option.none();
        }
        return Option.of(cursor.consume(stream -> stream.foldLeft(createAggregateInstance(), (i, e) -> {
            handlers.get(e.getClass())
                    .peek(h -> invokeQuietly(i, e, h));
            return i;
        })));
    }

    private Object invokeQuietly(TRoot i, Event e, Method h) {
        try {
            return h.invoke(i, e);
        } catch (IllegalAccessException | InvocationTargetException e1) {
            throw new EventStoreException(e1);
        }
    }

    private TRoot createAggregateInstance() {
        try {
            return (TRoot) typeToken.getRawType().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EventStoreException(e);
        }
    }

    @Override
    public boolean exists(TId tId) {
        return eventStore.count(tId, typeToken.getRawType()) > 0;
    }

    @Override
    public void add(TRoot tRoot) {

    }

    @Override
    public void delete(TRoot tRoot) {
        eventStore.markAllAsDeleted(tRoot.getId(), type());
    }

    private Class<TRoot> type() {
        return (Class<TRoot>) typeToken.getRawType();
    }

    protected EventStore eventStore;
    private Map<Class<?>, Method> handlers;
    private final TypeToken<TRoot> typeToken = new TypeToken<TRoot>(getClass()) {
    };
}
