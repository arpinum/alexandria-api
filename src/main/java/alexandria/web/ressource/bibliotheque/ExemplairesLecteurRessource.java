package alexandria.web.ressource.bibliotheque;

import alexandria.command.bibliotheque.AjoutExemplaireCommande;
import arpinum.command.CommandBus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

@Path("/lecteurs/{idBibliotheque}/exemplaires/{isbn}")
public class ExemplairesLecteurRessource {

    @Inject
    public ExemplairesLecteurRessource(CommandBus bus) {
        this.bus = bus;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void ajoute(@Suspended AsyncResponse response
            , @PathParam("idBibliotheque") String idBibliothèque
            , @PathParam("isbn") String isbn) throws ExecutionException {
        final AjoutExemplaireCommande commande = new AjoutExemplaireCommande();
        commande.idBibliotheque = idBibliothèque;
        commande.isbn = isbn;
        bus.send(commande);
    }

    private final CommandBus bus;
}
