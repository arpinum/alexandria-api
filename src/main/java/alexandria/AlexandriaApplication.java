package alexandria;

import alexandria.configuration.ConfigurationGuice;
import arpinum.configuration.JacksonConfiguration;
import arpinum.infrastructure.bus.guice.ScanMagique;
import com.google.inject.Injector;

import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;

public class AlexandriaApplication extends Application {

    public AlexandriaApplication(Injector parentInjector) {
        injector = parentInjector.createChildInjector(new ConfigurationGuice());
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> instances = ScanMagique.searchForAnnotatedClass("alexandria.web", Path.class)
                .stream().map(injector::getInstance)
                .collect(Collectors.toSet());
        instances.add(new JacksonConfiguration());
        return instances;
    }

    private final Injector injector;
}
