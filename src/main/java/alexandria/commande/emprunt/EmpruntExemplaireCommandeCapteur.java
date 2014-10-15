package alexandria.commande.emprunt;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.lecteur.Lecteur;
import fr.arpinum.graine.commande.CapteurCommande;

import java.util.UUID;

public class EmpruntExemplaireCommandeCapteur implements CapteurCommande<EmpruntExemplaireCommande, UUID> {

    @Override
    public UUID execute(EmpruntExemplaireCommande commande) {
        final Lecteur lecteur = new Lecteur(commande.emailLecteur);
        final Bibliotheque bibliotheque = LocalisateurEntrepots.bibliotheques().get(commande.identifiantBibliotheque);
        final Emprunt emprunt = lecteur.emprunte(bibliotheque, commande.isbn);
        LocalisateurEntrepots.emprunts().ajoute(emprunt);
        return emprunt.getId();
    }
}
