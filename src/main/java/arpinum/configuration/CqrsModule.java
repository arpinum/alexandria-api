package arpinum.configuration;

import arpinum.command.CommandBus;
import arpinum.command.CommandHandler;
import arpinum.command.CommandMiddleware;
import arpinum.command.CommandValidator;
import arpinum.infrastructure.bus.command.CommandBusAsynchronous;
import arpinum.infrastructure.bus.event.EventDispatcherMiddleware;
import arpinum.infrastructure.bus.guice.ScanMagique;
import arpinum.infrastructure.bus.query.QueryBusAsynchronous;
import arpinum.infrastructure.persistance.eventsourcing.EventStoreMiddleware;
import arpinum.query.QueryBus;
import arpinum.query.QueryHandler;
import arpinum.query.QueryMiddleware;
import arpinum.saga.SagaHandler;
import arpinum.saga.SagaMiddleware;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;

import javax.validation.Validation;
import javax.validation.Validator;

public class CqrsModule extends AbstractModule {

    public CqrsModule(String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected void configure() {
        confgureSagas();
        configureCommands();
        configureQuery();
    }

    private void confgureSagas() {
        ScanMagique.scanPackageAndBind(packageName + ".saga", SagaHandler.class, binder());
        bind(SagaMiddleware.class).asEagerSingleton();
    }

    private void configureCommands() {
        final Multibinder<CommandMiddleware> multibinder = Multibinder.newSetBinder(binder(), CommandMiddleware.class);
        multibinder.addBinding().to(CommandValidator.class);
        multibinder.addBinding().to(SagaMiddleware.class);
        multibinder.addBinding().to(EventDispatcherMiddleware.class);
        multibinder.addBinding().to(EventStoreMiddleware.class);
        ScanMagique.scanPackageAndBind(commandPackage(), CommandHandler.class, binder());
        bind(CommandBus.class).to(CommandBusAsynchronous.class).asEagerSingleton();
    }

    private String commandPackage() {
        return packageName + ".command";
    }

    private void configureQuery() {
        final Multibinder<QueryMiddleware> multibinder = Multibinder.newSetBinder(binder(), QueryMiddleware.class);
        ScanMagique.scanPackageAndBind(queryPackage(), QueryHandler.class, binder());
        bind(QueryBus.class).to(QueryBusAsynchronous.class).asEagerSingleton();
    }

    private String queryPackage() {
        return packageName + ".query";
    }

    @Provides
    @Singleton
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    private String packageName;
}
