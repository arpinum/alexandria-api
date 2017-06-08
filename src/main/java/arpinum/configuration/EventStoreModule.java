package arpinum.configuration;


import arpinum.ddd.event.EventStore;
import arpinum.infrastructure.persistance.eventsourcing.EventStoreJongo;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class EventStoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EventStore.class).to(EventStoreJongo.class).in(Singleton.class);
    }
}
