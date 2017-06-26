package alexandria.command.bibliotheque;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.exemplaire.Exemplaire;
import arpinum.command.VoidCommandHandler;
import arpinum.ddd.BusinessError;
import arpinum.ddd.event.Event;
import io.vavr.collection.*;

public class RendExemplaireCommandeCapteur implements VoidCommandHandler<RendExemplaireCommande> {

    @Override
    public Seq<Event> doExecute(RendExemplaireCommande commande) {
        Bibliotheque bibliotheque = LocalisateurEntrepots.bibliotheques().get(commande.idBibliothèque)
                .getOrElseThrow(() -> new BusinessError("PAS_TROUVÉ"));
        Exemplaire exemplaire = LocalisateurEntrepots.exemplaires().get(commande.idExemplaire)
                .getOrElseThrow(() -> new BusinessError("PAS_TROUVÉ"));
        return Vector.of(bibliotheque.rend(exemplaire));
    }
}
