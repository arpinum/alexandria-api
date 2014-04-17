package alexandria.commande.bibliotheque;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.lecteur.Lecteur;
import fr.arpinum.graine.commande.HandlerCommande;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class AjoutExemplaireCommandeHandler implements HandlerCommande<AjoutExemplaireCommande, UUID> {

    @Override
    public UUID execute(AjoutExemplaireCommande commande) {
        final Optional<Bibliotheque> eventuellemnent = LocalisateurEntrepots.bibliotheques().parEmailLecteur(commande.email);
        final Bibliotheque bibliotheque = eventuellemnent.orElseGet(créeBibliothèque(commande));
        bibliotheque.ajouteExemplaire(commande.isbn);
        return bibliotheque.getId();
    }

    private Supplier<Bibliotheque> créeBibliothèque(AjoutExemplaireCommande commande) {
        return () -> {
            final Bibliotheque résultat = new Bibliotheque(new Lecteur(commande.email));
            LocalisateurEntrepots.bibliotheques().ajoute(résultat);
            return résultat;
        };
    }
}
