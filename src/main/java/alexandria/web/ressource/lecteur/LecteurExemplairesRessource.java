package alexandria.web.ressource.lecteur;

import alexandria.saga.bibliiotheque.AjouteExemplaireBibliothèqueParDéfautSaga;
import arpinum.command.CommandBus;
import arpinum.configuration.Secured;
import io.vavr.collection.HashMap;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import java.util.concurrent.ExecutionException;

@Path("/lecteur/exemplaires")
@Secured
public class LecteurExemplairesRessource {

    @Inject
    public LecteurExemplairesRessource(CommandBus bus) {
        this.bus = bus;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void ajoute(@Suspended AsyncResponse response
            , @Context SecurityContext securityContext
            , AjouteExemplaireBibliothèqueParDéfautSaga commande) throws ExecutionException {
        commande.idLecteur = securityContext.getUserPrincipal().getName();
        bus.send(commande).map(id-> HashMap.of("id", id))
                .onSuccess(response::resume)
                .onFailure(f -> {
                    LoggerFactory.getLogger(LecteurExemplairesRessource.class).error("Erreur", f);
                    response.resume(f);
                });
    }

    private final CommandBus bus;
}
