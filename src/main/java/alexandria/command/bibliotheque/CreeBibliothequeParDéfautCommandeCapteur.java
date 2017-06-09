package alexandria.command.bibliotheque;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import arpinum.command.CommandHandler;
import arpinum.ddd.event.Event;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;


public class CreeBibliothequeParDéfautCommandeCapteur implements CommandHandler<CreeBibliothequeParDéfautCommande, String> {
    @Override
    public Tuple2<String, Seq<Event>> execute(CreeBibliothequeParDéfautCommande commande) {
        if(LocalisateurEntrepots.bibliotheques().exists(commande.lecteur.getId())) {
            return Tuple.of(commande.lecteur.getId(), Vector.empty());
        }
        return Bibliotheque.fabrique().pour(commande.lecteur)
                .swap().map(Bibliotheque::getId, e -> (Seq<Event>) Vector.of((Event)e));
    }
}
