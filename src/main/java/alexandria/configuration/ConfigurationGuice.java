package alexandria.configuration;

import alexandria.infrastructure.persistance.eventsource.LocalisateurEntrepotsEventSource;
import alexandria.modele.LocalisateurEntrepots;
import arpinum.configuration.CqrsModule;
import arpinum.configuration.EventBusModule;
import arpinum.configuration.EventStoreModule;
import catalogue.CatalogueLivre;
import catalogue.googlebooks.CatalogueLivreGoogle;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class ConfigurationGuice extends AbstractModule {
    @Override
    protected void configure() {
        configureCatalogue();
        binder().bind(Key.get(String.class, Names.named("boundedcontext.name"))).toInstance("alexandria");
        install(new EventBusModule("alexandria"));
        install(new EventStoreModule());
        install(new CqrsModule("alexandria"));
        bind(LocalisateurEntrepots.class).to(LocalisateurEntrepotsEventSource.class).in(Singleton.class);
        requestStaticInjection(LocalisateurEntrepots.class);
    }

    private void configureCatalogue() {
        bind(CatalogueLivre.class).to(CatalogueLivreGoogle.class).in(Singleton.class);
    }

}
