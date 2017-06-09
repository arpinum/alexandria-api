package alexandria.command.bibliotheque;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.modele.bibliotheque.IdentifiantExemplaire;
import arpinum.command.CommandHandler;
import arpinum.ddd.BusinessError;
import arpinum.ddd.event.Event;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;

public class AjoutExemplaireCommandeCapteur implements CommandHandler<AjoutExemplaireCommande, IdentifiantExemplaire> {

    @Override
    public Tuple2<IdentifiantExemplaire, Seq<Event>> execute(AjoutExemplaireCommande commande) {
        return LocalisateurEntrepots.bibliotheques().get(commande.idBibliotheque)
                .map(bibliotheque -> ajoute(bibliotheque, commande))
                .getOrElseThrow(() -> new BusinessError("BIBLIOTHEQUE_INCONNUCE"));
    }

    private Tuple2<IdentifiantExemplaire, Seq<Event>> ajoute(Bibliotheque bibliotheque, AjoutExemplaireCommande commande) {
        Tuple2<ExemplaireAjouteEvenement, Exemplaire> résultat = bibliotheque.ajouteExemplaire(commande.isbn);
        return résultat
                .swap()
                .map(e -> e.identifiant(), e -> (Seq<Event>) Vector.of((Event) e));
    }

}
