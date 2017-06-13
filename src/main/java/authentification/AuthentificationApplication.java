package authentification;

import arpinum.infrastructure.bus.guice.ScanMagique;
import authentification.application.UtilisateurService;
import authentification.configuration.ConfigurationGuice;
import com.google.inject.Injector;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationPath("/identite")
public class AuthentificationApplication extends Application {

    public AuthentificationApplication(Injector parentInjector) {
        injector = parentInjector.createChildInjector(new ConfigurationGuice());
    }

    @Override
    public Set<Object> getSingletons() {
        Set<Object> instances = ScanMagique.searchForAnnotatedClass("authentification.web", Path.class)
                .stream().map(injector::getInstance)
                .collect(Collectors.toSet());
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        instances.add(corsFilter);
        return instances;
    }

    public UtilisateurService utilisateurService() {
        return injector.getInstance(UtilisateurService.class);
    }

    private final Injector injector;
}
