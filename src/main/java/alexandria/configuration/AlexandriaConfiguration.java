package alexandria.configuration;

import alexandria.infrastructure.identite.RegistreLecteursDirect;
import alexandria.infrastructure.persistance.eventsource.LocalisateurEntrepotsEventSource;
import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.configuration.CqrsModule;
import arpinum.configuration.EventBusModule;
import arpinum.configuration.EventStoreModule;
import arpinum.infrastructure.bus.Io;
import authentification.AuthentificationApplication;
import catalogue.CatalogueLivre;
import catalogue.googlebooks.CatalogueLivreGoogle;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import okhttp3.OkHttpClient;
import org.cfg4j.provider.ConfigurationProvider;

import java.util.concurrent.ExecutorService;

public class AlexandriaConfiguration extends AbstractModule {

    public AlexandriaConfiguration(AuthentificationApplication authentificationApplication) {
        this.authentificationApplication = authentificationApplication;
    }

    @Override
    protected void configure() {
        binder().bind(Key.get(String.class, Names.named("boundedcontext.name"))).toInstance("alexandria");
        install(new EventBusModule("alexandria"));
        install(new EventStoreModule());
        install(new CqrsModule("alexandria"));
        bind(LocalisateurEntrepots.class).to(LocalisateurEntrepotsEventSource.class).in(Singleton.class);
        requestStaticInjection(LocalisateurEntrepots.class);
    }

    @Provides
    @Singleton
    public RegistreLecteurs registreLecteurse(@Io ExecutorService service) {
        return new RegistreLecteursDirect(authentificationApplication.utilisateurService(), service);
    }

    @Provides
    public CatalogueLivre configureCatalogue(ConfigurationProvider provider, OkHttpClient client, @Io ExecutorService service) {
        String apiKey = provider.getProperty("google.apiKey", String.class);
        return new CatalogueLivreGoogle(service, client, apiKey);
    }

    @Provides
    @Singleton
    public OkHttpClient client() {
        return new OkHttpClient.Builder()
                .build();
    }

    private AuthentificationApplication authentificationApplication;

}
