package alexandria;

import alexandria.configuration.AlexandriaConfiguration;
import arpinum.configuration.JacksonConfiguration;
import arpinum.infrastructure.security.JwtFilter;
import arpinum.infrastructure.bus.guice.ScanMagique;
import authentification.AuthentificationApplication;
import com.google.inject.Injector;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationPath("/alexandria")
public class AlexandriaApplication extends Application {

    public AlexandriaApplication(Injector parentInjector, AuthentificationApplication authentificationApplication) {
        injector = parentInjector.createChildInjector(new AlexandriaConfiguration(authentificationApplication));
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> instances = ScanMagique.searchForAnnotatedClass("alexandria.web", Path.class)
                .stream().map(injector::getInstance)
                .collect(Collectors.toSet());
        instances.add(new JacksonConfiguration());
        instances.add(injector.getInstance(JwtFilter.class));
        return instances;
    }

    private final Injector injector;
}
