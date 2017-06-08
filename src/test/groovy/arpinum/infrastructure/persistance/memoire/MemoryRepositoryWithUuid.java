package arpinum.infrastructure.persistance.memoire;


import arpinum.ddd.AggregateWithUuid;
import arpinum.ddd.RepositoryWithUuid;

import java.util.UUID;

@SuppressWarnings("UnusedDeclaration")
public class MemoryRepositoryWithUuid<TRacine extends AggregateWithUuid> extends MemoryRepository<UUID, TRacine> implements RepositoryWithUuid<TRacine> {
}
