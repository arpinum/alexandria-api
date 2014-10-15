package alexandria.web.ressource.emprunt;

import alexandria.commande.emprunt.EmpruntExemplaireCommande;
import com.google.common.base.Throwables;
import fr.arpinum.graine.commande.BusCommande;
import fr.arpinum.graine.infrastructure.bus.ResultatExecution;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import java.util.UUID;

public class EmpruntsRessource extends ServerResource {

    @Inject
    public EmpruntsRessource(BusCommande busCommande) {
        this.busCommande = busCommande;
    }

    @Post
    public UUID emprunte(EmpruntExemplaireCommande commande) {
        final ResultatExecution<UUID> resultatExecution = busCommande.envoieEtAttendReponse(commande);
        if (!resultatExecution.isSucces()) {
            Throwables.propagate(resultatExecution.erreur());
        }
        return resultatExecution.donnees();

    }

    private final BusCommande busCommande;
}
