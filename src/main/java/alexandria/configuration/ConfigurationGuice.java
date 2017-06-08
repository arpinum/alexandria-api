package alexandria.configuration;

import alexandria.infrastructure.persistance.eventsource.LocalisateurEntrepotsEventSource;
import alexandria.modele.LocalisateurEntrepots;
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
    }

    @Provides
    public CatalogueLivre configureCatalogue(ConfigurationProvider provider, OkHttpClient client) {
        String apiKey = provider.getProperty("google.apiKey", String.class);
        return new CatalogueLivreGoogle(client, apiKey);
    }

    @Provides
    @Singleton
    public OkHttpClient client(@Io ExecutorService service) {
        return new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(service))
                .build();
    }

}
