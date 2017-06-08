package alexandria.web.ressource.livre;

import alexandria.query.livre.resume.recherche.TousLesLivres;
import arpinum.query.QueryBus;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

@Path("/livres")
public class LivresRessource {

    @Inject
    public LivresRessource(QueryBus busRecherche) {
        this.busRecherche = busRecherche;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void recherche(@Suspended AsyncResponse response) {
        busRecherche.send(new TousLesLivres())
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }

    private final QueryBus busRecherche;
}
