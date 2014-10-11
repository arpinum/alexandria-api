package alexandria.web.ressource.bibliotheque;

import alexandria.commande.bibliotheque.AjoutExemplaireCommande;
import fr.arpinum.graine.commande.BusCommande;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

public class ExemplairesLecteurRessource extends ServerResource {

    @Inject
    public ExemplairesLecteurRessource(BusCommande bus) {
        this.bus = bus;
    }

    @Put("application/json")
    public void ajoute() throws ExecutionException {
        final AjoutExemplaireCommande commande = new AjoutExemplaireCommande();
        commande.email = getAttribute("email");
        commande.isbn = getAttribute("isbn");
        bus.envoieEtAttendReponse(commande);
    }

    private final BusCommande bus;
}
