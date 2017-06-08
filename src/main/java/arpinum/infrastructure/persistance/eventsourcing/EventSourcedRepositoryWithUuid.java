package arpinum.infrastructure.persistance.eventsourcing;


import arpinum.ddd.BaseAggregate;
import arpinum.ddd.event.EventStore;

import java.util.UUID;

public class EventSourcedRepositoryWithUuid<TRoot extends BaseAggregate<UUID>> extends EventSourcedRepository<UUID, TRoot> {

    public EventSourcedRepositoryWithUuid(EventStore eventStore) {
        super(eventStore);
    }
}
