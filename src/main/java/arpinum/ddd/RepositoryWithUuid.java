package arpinum.ddd;


import java.util.UUID;

public interface RepositoryWithUuid<TRacine extends AggregateWithUuid> extends Repository<UUID, TRacine> {
}
