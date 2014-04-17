package alexandria.web.configuration;

import catalogue.CatalogueLivre;
import catalogue.googlebooks.CatalogueLivreGoogle;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.commande.HandlerCommande;
import fr.arpinum.graine.commande.SynchronisationCommande;
import fr.arpinum.graine.commande.ValidateurCommande;
import fr.arpinum.graine.infrastructure.bus.guice.BusMagique;
import fr.arpinum.graine.infrastructure.persistance.mongo.ContexteMongoLink;
import fr.arpinum.graine.modele.evenement.BusEvenement;
import fr.arpinum.graine.modele.evenement.BusEvenementAsynchrone;
import fr.arpinum.graine.modele.evenement.HandlerEvenement;
import fr.arpinum.graine.modele.evenement.SynchronisationEvenement;
import fr.arpinum.graine.recherche.BusRecherche;
import fr.arpinum.graine.recherche.HandlerRecherche;
import org.jongo.Jongo;
import org.mongolink.MongoSessionManager;
import org.mongolink.Settings;
import org.mongolink.domain.UpdateStrategies;
import org.mongolink.domain.mapper.ContextBuilder;
import alexandria.infrastructure.persistance.mongo.LocalisateurEntrepotsMongoLink;
import alexandria.modele.LocalisateurEntrepots;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Properties;

public class ConfigurationGuice extends AbstractModule {
    @Override
    protected void configure() {
        Names.bindProperties(binder(), propriétés());
        configureCatalogue();
        configurePersistance();
        configureCommandes();
        configureRecherches();
        configureEvements();

    }

    private void configureCatalogue() {
        bind(CatalogueLivre.class).to(CatalogueLivreGoogle.class).in(Singleton.class);
    }

    private Properties propriétés() {
        URL url = Resources.getResource("env/" + Optional.ofNullable(System.getenv("env")).orElse("dev") + "/application.properties");
        ByteSource inputSupplier = Resources
                .asByteSource(url);
        Properties propriétés = new Properties();
        try {
            propriétés.load(inputSupplier.openStream());
        } catch (IOException e) {

        }
        return propriétés;
    }

    private void configurePersistance() {
        bind(ContexteMongoLink.class).in(Singleton.class);

        bind(LocalisateurEntrepots.class).to(LocalisateurEntrepotsMongoLink.class).in(Singleton.class);
    }

    private void configureRecherches() {
        BusMagique.scanPackageEtBind("alexandria.recherche", HandlerRecherche.class, binder());
        bind(BusRecherche.class).asEagerSingleton();
    }

    private void configureCommandes() {
        final Multibinder<SynchronisationCommande> multibinder = Multibinder.newSetBinder(binder(), SynchronisationCommande.class);
        multibinder.addBinding().to(ValidateurCommande.class);
        multibinder.addBinding().to(ContexteMongoLink.class);
        BusMagique.scanPackageEtBind("alexandria.commande", HandlerCommande.class, binder());
        bind(BusCommande.class).asEagerSingleton();
    }

    private void configureEvements() {
        final Multibinder<SynchronisationEvenement> multibinder = Multibinder.newSetBinder(binder(), SynchronisationEvenement.class);
        multibinder.addBinding().to(ContexteMongoLink.class);
        BusMagique.scanPackageEtBind("alexandria", HandlerEvenement.class, binder());
        bind(BusEvenement.class).to(BusEvenementAsynchrone.class).asEagerSingleton();
    }

    @Provides
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Provides
    @Singleton
    public MongoSessionManager mongoLink(ConfigurationMongoDb configurationMongoDb) {
        Settings settings = Settings.defaultInstance().withDefaultUpdateStrategy(UpdateStrategies.DIFF)
                .withDbName(configurationMongoDb.name)
                .withHost(configurationMongoDb.host)
                .withPort(configurationMongoDb.port);
        if (configurationMongoDb.avecAuthentificationDB()) {
            settings = settings.withAuthentication(configurationMongoDb.user, configurationMongoDb.password);
        }

        return MongoSessionManager.create(new ContextBuilder("alexandria.infrastructure.persistance.mongo.mapping"),
                settings);
    }

    @Provides
    @Singleton
    public Jongo jongo(ConfigurationMongoDb configurationMongoDb) throws UnknownHostException {
        final MongoClient mongoClient = new MongoClient(configurationMongoDb.host, configurationMongoDb.port);
        final DB db = mongoClient.getDB(configurationMongoDb.name);
        if (configurationMongoDb.avecAuthentificationDB()) {
            assert db.authenticate(configurationMongoDb.user, configurationMongoDb.password.toCharArray());
        }
        return new Jongo(db);
    }
}
