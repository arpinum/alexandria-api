package alexandria.web.ressource.emprunt;

import alexandria.commande.emprunt.RendEmpruntCommande;
import fr.arpinum.graine.commande.BusCommande;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import java.util.UUID;

public class EmpruntRessource extends ServerResource {

    @Inject
    public EmpruntRessource(BusCommande busCommande) {
        this.busCommande = busCommande;
    }

    @Put
    public void rend(){
        final RendEmpruntCommande commande = new RendEmpruntCommande();
        commande.isbn = getAttribute("isbn");
        commande.idBibliotheque = UUID.fromString(getAttribute("idBibliotheque"));
        busCommande.envoie(commande);
    }

    private final BusCommande busCommande;
}
