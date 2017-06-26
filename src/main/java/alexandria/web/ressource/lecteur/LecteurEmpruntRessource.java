package alexandria.web.ressource.lecteur;

import alexandria.command.bibliotheque.RendExemplaireCommande;
import arpinum.command.CommandBus;
import io.vavr.collection.HashMap;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("/lecteur/emprunts/{bibliotheque}:{exemplaire}")
public class LecteurEmpruntRessource {

    @Inject
    public LecteurEmpruntRessource(CommandBus bus) {
        this.bus = bus;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void rend(@Suspended AsyncResponse response, @PathParam("bibliotheque") String idBibliotheque, @PathParam("exemplaire") UUID exemplaire) {
        bus.send(new RendExemplaireCommande(idBibliotheque, exemplaire))
                .map(n -> HashMap.of("status", "youpi"))
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }

    private CommandBus bus;
}
