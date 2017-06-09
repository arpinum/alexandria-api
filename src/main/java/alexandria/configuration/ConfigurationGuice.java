package alexandria.configuration;

import alexandria.infrastructure.persistance.eventsource.LocalisateurEntrepotsEventSource;
import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.lecteur.FicheLecteur;
import alexandria.modele.lecteur.Lecteur;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.configuration.CqrsModule;
import arpinum.configuration.EventBusModule;
import arpinum.configuration.EventStoreModule;
import arpinum.infrastructure.bus.Io;
import catalogue.CatalogueLivre;
import catalogue.googlebooks.CatalogueLivreGoogle;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import io.vavr.concurrent.Future;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import org.cfg4j.provider.ConfigurationProvider;

import java.util.concurrent.ExecutorService;

public class ConfigurationGuice extends AbstractModule {
    @Override
    protected void configure() {
        binder().bind(Key.get(String.class, Names.named("boundedcontext.name"))).toInstance("alexandria");
        install(new EventBusModule("alexandria"));
        install(new EventStoreModule());
        install(new CqrsModule("alexandria"));
        bind(LocalisateurEntrepots.class).to(LocalisateurEntrepotsEventSource.class).in(Singleton.class);
        requestStaticInjection(LocalisateurEntrepots.class);
        bind(RegistreLecteurs.class).toInstance(new RegistreLecteurs() {
            @Override
            public Future<Lecteur> trouve(String id) {
                return Future.successful(new Lecteur(id));
            }

            @Override
            public Future<FicheLecteur> ficheDe(String id) {
                return Future.successful(new FicheLecteur("Doe", "John"));
            }
        });
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

}