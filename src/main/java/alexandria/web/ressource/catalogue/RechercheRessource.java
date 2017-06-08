package alexandria.web.ressource.catalogue;

import catalogue.CatalogueLivre;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/recherche")
public class RechercheRessource {

    @Inject
    public RechercheRessource(CatalogueLivre catalogueLivre) {
        this.catalogueLivre = catalogueLivre;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void recherche(@Suspended AsyncResponse response, @QueryParam("q") String q) {
        catalogueLivre.recherche(q)
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }

    private final CatalogueLivre catalogueLivre;
}
