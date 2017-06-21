package alexandria.web.ressource.livre;

import alexandria.query.exemplaire.resume.recherche.ExemplairesParIsbn;
import arpinum.query.QueryBus;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MediaType;

@Path("/livres/{isbn}/exemplaires")
public class LivreExemplairesResource {

    @Inject
    public LivreExemplairesResource(QueryBus queryBus) {
        this.queryBus = queryBus;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void recherche(@Suspended AsyncResponse response, @PathParam("isbn") String isbn) {
        ExemplairesParIsbn recherche = new ExemplairesParIsbn(isbn);
        queryBus.send(recherche)
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }
    private QueryBus queryBus;
}
