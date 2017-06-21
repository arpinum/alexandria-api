package alexandria.command.bibliotheque;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.exemplaire.*;
import arpinum.command.CommandHandler;
import arpinum.ddd.BusinessError;
import arpinum.ddd.event.Event;
import io.vavr.Tuple2;
import io.vavr.collection.*;

import java.util.UUID;

public class AjoutExemplaireCommandeCapteur implements CommandHandler<AjoutExemplaireCommande, UUID> {

    @Override
    public Tuple2<UUID, Seq<Event>> execute(AjoutExemplaireCommande commande) {
        return LocalisateurEntrepots.bibliotheques().get(commande.idBibliotheque)
                .map(bibliotheque -> ajoute(bibliotheque, commande))
                .getOrElseThrow(() -> new BusinessError("BIBLIOTHEQUE_INCONNUCE"));
    }

    private Tuple2<UUID, Seq<Event>> ajoute(Bibliotheque bibliotheque, AjoutExemplaireCommande commande) {
        Tuple2<ExemplaireAjouté, Exemplaire> résultat = bibliotheque.ajouteExemplaire(commande.isbn);
        return résultat
                .swap()
                .map(e -> e.getId(), e -> (Seq<Event>) Vector.of((Event) e));
    }

}
