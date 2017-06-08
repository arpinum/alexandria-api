package arpinum.infrastructure.persistance.memoire;

import arpinum.ddd.AggregateRoot;
import arpinum.ddd.Repository;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.control.Option;


@SuppressWarnings("UnusedDeclaration")
public class MemoryRepository<TId, TRacine extends AggregateRoot<TId>> implements Repository<TId, TRacine> {

    @Override
    public Option<TRacine> get(TId tId) {
        return entities.filter(element -> element.getId().equals(tId))
                .headOption();
    }

    @Override
    public boolean exists(TId tId) {
        return !get(tId).isEmpty();
    }

    @Override
    public void add(TRacine racine) {
        entities = entities.add(racine);
    }

    @Override
    public void delete(TRacine racine) {
        entities.remove(racine);
    }

    public Set<TRacine> getAll() {
        return entities;
    }

    protected  HashSet<TRacine> entities = HashSet.empty();
}
