package arpinum.configuration;


import arpinum.infrastructure.bus.Computation;
import arpinum.infrastructure.bus.Io;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.AbstractModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExecutorService.class)
                .annotatedWith(Computation.class)
                .toInstance(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()
                        , new ThreadFactoryBuilder()
                                .setNameFormat("computation-pool-%d").build()));

        bind(ExecutorService.class)
                .annotatedWith(Io.class)
                .toInstance(Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                        .setNameFormat("io-pool-%d").build()));
    }
}
