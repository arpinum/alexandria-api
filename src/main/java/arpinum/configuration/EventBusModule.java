package arpinum.configuration;

import arpinum.ddd.event.EventBus;
import arpinum.ddd.event.EventBusMiddleware;
import arpinum.ddd.event.EventCaptor;
import arpinum.infrastructure.bus.event.EventBusAsynchronous;
import arpinum.infrastructure.bus.guice.ScanMagique;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

public class EventBusModule extends AbstractModule {

    public EventBusModule(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected void configure() {
        configureEventBus();

    }

    private void configureEventBus() {
        final Multibinder<EventBusMiddleware> multibinder = Multibinder.newSetBinder(binder(), EventBusMiddleware.class);
        ScanMagique.scanPackageAndBind(packageName, EventCaptor.class, binder());
        bind(EventBus.class).to(EventBusAsynchronous.class).asEagerSingleton();
    }

    private String packageName;
}
