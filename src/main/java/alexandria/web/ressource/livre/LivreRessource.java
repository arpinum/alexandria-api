package alexandria.web.ressource.livre;

import alexandria.query.livre.details.recherche.RechercheDetailsLivre;
import arpinum.query.QueryBus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/livres/{isbn}")
public class LivreRessource {

    @Inject
    public LivreRessource(QueryBus recherche) {
        this.recherche = recherche;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void recherche(@Suspended AsyncResponse response, @PathParam("isbn") String isbn) {
        recherche.send(new RechercheDetailsLivre(isbn))
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }

    private final QueryBus recherche;
}
