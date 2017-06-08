package arpinum.ddd;


import io.vavr.control.Option;

public interface Repository<TId, TRacine extends AggregateRoot<TId>> {

    Option<TRacine> get(TId id);

    boolean exists(TId id);

    void add(TRacine racine);

    void delete(TRacine racine);
}
