package alexandria.web.ressource.bibliotheque;

import alexandria.commande.bibliotheque.AjoutExemplaireCommande;
import fr.arpinum.graine.commande.BusCommande;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class ExemplairesLecteurRessource extends ServerResource {

    @Inject
    public ExemplairesLecteurRessource(BusCommande bus) {
        this.bus = bus;
    }

    @Put
    public void ajoute() {
        final AjoutExemplaireCommande commande = new AjoutExemplaireCommande();
        commande.email = getAttribute("email");
        commande.isbn = getAttribute("isbn");
        bus.poste(commande);
    }

    private final BusCommande bus;
}
