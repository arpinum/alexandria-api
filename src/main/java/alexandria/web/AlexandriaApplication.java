package alexandria.web;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.web.configuration.ConfigurationGuice;
import alexandria.web.ressource.IndexRessource;
import alexandria.web.ressource.bibliotheque.ExemplairesLecteurRessource;
import alexandria.web.ressource.catalogue.RechercheRessource;
import alexandria.web.ressource.livre.LivreRessource;
import alexandria.web.ressource.livre.LivresRessource;
import com.google.inject.*;
import fr.arpinum.graine.modele.evenement.BusEvenement;
import fr.arpinum.graine.modele.evenement.LocalisateurBusEvenement;
import fr.arpinum.graine.web.restlet.BaseApplication;
import fr.arpinum.graine.web.restlet.router.GuiceRouter;
import org.restlet.Context;

import java.util.Optional;

public class AlexandriaApplication extends BaseApplication {

    public AlexandriaApplication(Context context) {
        super(context);
        injector = Guice.createInjector(stage(), new ConfigurationGuice());
        LocalisateurBusEvenement.initialise(injector.getInstance(BusEvenement.class));
        LocalisateurEntrepots.initialise(injector.getInstance(LocalisateurEntrepots.class));
    }

    private Stage stage() {
        final Optional<String> env = Optional.ofNullable(System.getenv("env"));
        LOGGER.info("Configuration en mode {}", env.orElse("dev"));
        if (env.orElse("dev").equals("dev")) {
            return Stage.DEVELOPMENT;
        }
        return Stage.PRODUCTION;
    }


    @Override
    protected GuiceRouter routes() {
        return new GuiceRouter(getContext(), injector) {
            @Override
            protected void route() {
                attach("/livres", LivresRessource.class);
                attach("/livres/{isbn}", LivreRessource.class);
                attach("/recherche", RechercheRessource.class);
                attach("/lecteurs/{email}/exemplaires/{isbn}", ExemplairesLecteurRessource.class);
                attachDefault(IndexRessource.class);
            }
        };
    }

    private final Injector injector;
}
