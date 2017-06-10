package arpinum.configuration;


import arpinum.infrastructure.bus.Computation;
import arpinum.infrastructure.bus.Io;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.AbstractModule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExecutorService.class)
                .annotatedWith(Computation.class)
                .toInstance(MoreExecutors.newDirectExecutorService());
                //.toInstance(createExecutore("computation-pool-%d"));

        bind(ExecutorService.class)
                .annotatedWith(Io.class)
                .toInstance(createExecutore("io-pool-%d"));
    }

    private ExecutorService createExecutore(String poolFormat) {
        return Executors.newCachedThreadPool(new ThreadFactoryBuilder()
                .setNameFormat(poolFormat).build());
    }
}
