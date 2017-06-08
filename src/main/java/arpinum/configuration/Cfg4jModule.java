package arpinum.configuration;


import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import io.vavr.collection.List;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.classpath.ClasspathConfigurationSource;
import org.cfg4j.source.compose.FallbackConfigurationSource;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.files.FilesConfigurationSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;

public class Cfg4jModule extends AbstractModule {


    public Cfg4jModule(String... paths) {
        this.paths = List.of(paths)
                .map(p -> Paths.get(p));
    }

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    ConfigurationProvider configuration() {

        ClasspathConfigurationSource classpath = new ClasspathConfigurationSource(() -> paths);
        FilesConfigurationSource files = new FilesConfigurationSource(() -> paths);
        ImmutableEnvironment env = new ImmutableEnvironment("config");
        return new ConfigurationProviderBuilder()
                .withConfigurationSource(new FallbackConfigurationSource(files, classpath))
                .withEnvironment(env)
                .build();
    }

    private List<Path> paths;
}
