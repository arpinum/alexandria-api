package alexandria.command.bibliotheque;


import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.Emprunt;
import alexandria.modele.exemplaire.Exemplaire;
import arpinum.command.CommandHandler;
import arpinum.ddd.BusinessError;
import arpinum.ddd.event.Event;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;

public class SortExemplaireCommandeCapteur implements CommandHandler<SortExemplaireCommande, Emprunt> {

    @Override
    public Tuple2<Emprunt, Seq<Event>> execute(SortExemplaireCommande commande) {
        return bibliothèque(commande).sort(exemplaire(commande), commande.lecteur)
                .swap()
                .map(e -> e, Vector::of);
    }

    private Exemplaire exemplaire(SortExemplaireCommande commande) {
        return LocalisateurEntrepots.exemplaires().get(commande.idExemplaire)
                .getOrElseThrow(() -> new BusinessError("EXEMPLAIRE_INCONNU"));
    }

    private Bibliotheque bibliothèque(SortExemplaireCommande commande) {
        return LocalisateurEntrepots.bibliotheques().get(commande.idBibliothèque)
                .getOrElseThrow(() -> new BusinessError("BIBLIOTEQUE_INCONNU"));
    }

}
