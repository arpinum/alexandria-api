package alexandria.web.ressource.catalogue;

import catalogue.CatalogueLivre;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class RechercheRessource extends ServerResource {

    @Inject
    public RechercheRessource(CatalogueLivre catalogueLivre) {
        this.catalogueLivre = catalogueLivre;
    }

    @Get
    public ListeDetailsLivres recherche() {
        return ListeDetailsLivres.nouvelle(catalogueLivre.recherche(getQueryValue("q")));
    }

    private final CatalogueLivre catalogueLivre;
}
