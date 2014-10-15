package alexandria.web.ressource.livre;

import alexandria.recherche.livre.resume.modele.ResumeLivre;
import alexandria.recherche.livre.resume.recherche.TousLesLivres;
import fr.arpinum.graine.recherche.BusRecherche;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class LivresRessource extends ServerResource {

    @Inject
    public LivresRessource(BusRecherche busRecherche) {
        this.busRecherche = busRecherche;
    }

    @Get
    public Iterable<ResumeLivre> recherche() {
        return busRecherche.envoieEtAttendReponse(new TousLesLivres()).donnees();
    }

    private final BusRecherche busRecherche;
}
