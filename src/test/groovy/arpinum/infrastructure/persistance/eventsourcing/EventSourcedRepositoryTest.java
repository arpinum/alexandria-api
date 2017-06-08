package arpinum.infrastructure.persistance.eventsourcing;


import arpinum.ddd.BaseAggregateWithUuid;
import arpinum.ddd.event.Event;
import arpinum.ddd.event.EventBus;
import arpinum.ddd.event.EventSourceHandler;
import arpinum.ddd.event.EventStore;
import io.vavr.collection.List;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;

public class EventSourcedRepositoryTest {

    private EventStore eventStore;
    private EventBus bus;
    private EventSourcedRepository<UUID, EventSourcedAggregate> repository;

    @Before
    public void setUp() throws Exception {
        eventStore = new EventStoreMemory();
        bus = mock(EventBus.class);
        repository = new AggregateRepository(eventStore);
    }

    @Test
    public void returns_aggregate() throws Exception {
        UUID targetId = UUID.randomUUID();
        eventStore.save(Vector.of(new CreatedEvent(targetId)));

        Option<EventSourcedAggregate> aggregate = repository.get(targetId);

        assertThat(aggregate).isNotEmpty();

        assertThat(aggregate.get().getId()).isEqualTo(targetId);
    }

    @Test
    public void says_it_exists() throws Exception {
        UUID targetId = UUID.randomUUID();
        eventStore.save(Vector.of(new CreatedEvent(targetId)));

        boolean exists = repository.exists(targetId);

        assertThat(exists).isTrue();
    }

    @Test
    public void mark_events_as_deleted() throws Exception {
        UUID targetId = UUID.randomUUID();
        CreatedEvent event = new CreatedEvent(targetId);
        eventStore.save(List.of(event));
        EventSourcedAggregate aggregate = new EventSourcedAggregate(targetId);
        aggregate.handle(event);
        aggregate.genEvent(event);
        repository.add(aggregate);

        repository.delete(aggregate);

        assertThat(eventStore.count(targetId, EventSourcedAggregate.class)).isEqualTo(0);
    }



    public static class EventSourcedAggregate extends BaseAggregateWithUuid {

        public EventSourcedAggregate() {
        }

        public EventSourcedAggregate(UUID targetId) {
            super(targetId);
        }

        @EventSourceHandler
        public void handle(CreatedEvent event) {
            this.setId((UUID) event.getTargetId());
        }

        public void genEvent(Event event) {

        }
    }

    public static class CreatedEvent extends Event<EventSourcedAggregate> {

        public CreatedEvent(Object targetId) {
            super(targetId);
        }

    }

    public static class AggregateRepository extends EventSourcedRepository<UUID, EventSourcedAggregate> {

        public AggregateRepository( EventStore eventStore) {
            super(eventStore);
        }
    }
}
