package alexandria;

import alexandria.configuration.ConfigurationGuice;
import alexandria.infrastructure.identite.Pac4jProvider;
import arpinum.configuration.JacksonConfiguration;
import arpinum.infrastructure.bus.guice.ScanMagique;
import com.google.inject.Injector;
import org.pac4j.core.config.Config;
import org.pac4j.jax.rs.features.Pac4JSecurityFeature;
import org.pac4j.jax.rs.servlet.features.ServletJaxRsContextFactoryProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationPath("/alexandria")
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
        instances.add(injector.getInstance(Pac4jProvider.class));

        return instances;
    }

    private final Injector injector;
}
