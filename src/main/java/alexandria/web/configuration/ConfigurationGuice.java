package alexandria.web.configuration;

import alexandria.infrastructure.persistance.mongo.LocalisateurEntrepotsMongoLink;
import alexandria.modele.LocalisateurEntrepots;
import catalogue.CatalogueLivre;
import catalogue.googlebooks.CatalogueLivreGoogle;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.inject.*;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import fr.arpinum.graine.commande.*;
import fr.arpinum.graine.infrastructure.bus.guice.BusMagique;
import fr.arpinum.graine.infrastructure.persistance.mongo.ContexteMongoLink;
import fr.arpinum.graine.modele.evenement.*;
import fr.arpinum.graine.recherche.BusRecherche;
import fr.arpinum.graine.recherche.CapteurRecherche;
import org.jongo.Jongo;
import org.mongolink.*;
import org.mongolink.domain.mapper.ContextBuilder;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

import static com.google.common.base.Preconditions.*;

public class ConfigurationGuice extends AbstractModule {
    @Override
    protected void configure() {
        Names.bindProperties(binder(), propriétés());
        configureCatalogue();
        configurePersistance();
        configureEvements();
        configureCommandes();
        configureRecherches();
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
        BusMagique.scanPackageEtBind("alexandria.recherche", CapteurRecherche.class, binder());
        bind(BusRecherche.class).asEagerSingleton();
    }

    private void configureCommandes() {
        final Multibinder<SynchronisationCommande> multibinder = Multibinder.newSetBinder(binder(), SynchronisationCommande.class);
        multibinder.addBinding().to(ContexteMongoLink.class);
        multibinder.addBinding().to(ValidateurCommande.class);
        multibinder.addBinding().to(BusEvenementAsynchrone.class);
        BusMagique.scanPackageEtBind("alexandria.commande", CapteurCommande.class, binder());
        bind(BusCommande.class).asEagerSingleton();
    }

    private void configureEvements() {
        final Multibinder<SynchronisationEvenement> multibinder = Multibinder.newSetBinder(binder(), SynchronisationEvenement.class);
        multibinder.addBinding().to(ContexteMongoLink.class);
        BusMagique.scanPackageEtBind("alexandria", CapteurEvenement.class, binder());
        bind(BusEvenement.class).to(BusEvenementAsynchrone.class).asEagerSingleton();
    }

    @Provides
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Provides
    @Singleton
    BusEvenementAsynchrone busEvenementAsynchrone(Set<SynchronisationEvenement> synchronisationEvenements, Set<CapteurEvenement> evenements) {
        return new BusEvenementAsynchrone(synchronisationEvenements, evenements);
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
    public Jongo jongo(ConfigurationMongoDb configurationMongoDb, MongoSessionManager manager) throws UnknownHostException {
        final MongoClient mongoClient = new MongoClient(configurationMongoDb.host, configurationMongoDb.port);
        final DB db = mongoClient.getDB(configurationMongoDb.name);
        if (configurationMongoDb.avecAuthentificationDB()) {
            checkState(db.authenticate(configurationMongoDb.user, configurationMongoDb.password.toCharArray()));
        }
        return new Jongo(db);
    }
}
