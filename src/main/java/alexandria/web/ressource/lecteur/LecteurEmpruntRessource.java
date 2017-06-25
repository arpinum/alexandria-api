package alexandria.web.ressource.lecteur;

import alexandria.command.bibliotheque.RendExemplaireCommande;
import arpinum.command.CommandBus;
import io.vavr.collection.HashMap;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/lecteur/emprunts/{exemplaire}")
public class LecteurEmpruntRessource {

    @Inject
    public LecteurEmpruntRessource(CommandBus bus) {
        this.bus = bus;
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void rend(@Suspended AsyncResponse response, @PathParam("exemplaire") UUID idExemplaire, RendExemplaireCommande commande) {
        commande.idExemplaire = idExemplaire;
        bus.send(commande)
                .map(r -> HashMap.of("status", "ok"))
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }


    private CommandBus bus;
}
