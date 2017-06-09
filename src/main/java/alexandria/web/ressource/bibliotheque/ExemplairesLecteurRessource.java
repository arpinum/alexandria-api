package alexandria.web.ressource.bibliotheque;

import alexandria.saga.bibliiotheque.AjouteExemplaireBibliothèqueParDéfautSaga;
import arpinum.command.CommandBus;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

@Path("/ma/bibliotheque/exemplaires")
public class ExemplairesLecteurRessource {

    @Inject
    public ExemplairesLecteurRessource(CommandBus bus) {
        this.bus = bus;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void ajoute(@Suspended AsyncResponse response
            , AjouteExemplaireBibliothèqueParDéfautSaga commande) throws ExecutionException {

        commande.idLecteur = "hash";
        bus.send(commande).onSuccess(response::resume)
                .onFailure(f -> {
                    LoggerFactory.getLogger(ExemplairesLecteurRessource.class).error("Erreur", f);
                    response.resume(f);
                });
    }

    private final CommandBus bus;
}
