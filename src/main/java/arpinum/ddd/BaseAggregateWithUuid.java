package arpinum.ddd;

import java.util.UUID;

@SuppressWarnings("UnusedDeclaration")
public abstract class BaseAggregateWithUuid extends BaseAggregate<UUID> implements AggregateWithUuid {

    protected BaseAggregateWithUuid(UUID uuid) {
        super(uuid);
    }

    protected BaseAggregateWithUuid() {
        super(UUID.randomUUID());
    }
}
