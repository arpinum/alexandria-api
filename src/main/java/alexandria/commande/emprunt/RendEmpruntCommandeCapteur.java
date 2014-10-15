package alexandria.commande.emprunt;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
import fr.arpinum.graine.commande.CapteurCommande;

import java.util.Optional;

public class RendEmpruntCommandeCapteur implements CapteurCommande<RendEmpruntCommande, Void> {

    @Override
    public Void execute(RendEmpruntCommande commande) {
        final Optional<Emprunt> empruntEventuel = LocalisateurEntrepots.emprunts().empruntCourantDe(new Exemplaire(commande.isbn, commande.idBibliotheque));
        if(empruntEventuel.isPresent()) {
            empruntEventuel.get().rend();
        }
        return null;
    }
}
