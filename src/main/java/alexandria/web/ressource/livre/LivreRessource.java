package alexandria.web.ressource.livre;

import alexandria.recherche.livre.details.modele.Livre;
import alexandria.recherche.livre.details.recherche.RechercheDetailsLivre;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import fr.arpinum.graine.recherche.BusRecherche;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class LivreRessource extends ServerResource {

    @Inject
    public LivreRessource(BusRecherche recherche) {
        this.recherche = recherche;
    }

    @Get
    public Livre represente() {
        final ResultatExecution<Livre> résultat = recherche.envoieEtAttendReponse(new RechercheDetailsLivre(getAttribute("isbn")));
        return résultat.donnees();
    }

    private final BusRecherche recherche;
}
