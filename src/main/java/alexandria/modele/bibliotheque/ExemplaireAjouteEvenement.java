package alexandria.modele.bibliotheque;

import fr.arpinum.graine.modele.evenement.Evenement;
import fr.arpinum.graine.modele.evenement.Synchrone;

import java.util.UUID;

@Synchrone
public class ExemplaireAjouteEvenement implements Evenement {

    public ExemplaireAjouteEvenement(String isbn, UUID idBibliotheque) {
        this.isbn = isbn;
        this.idBibliotheque = idBibliotheque;
    }

    public final String isbn;
    public final UUID idBibliotheque;
}
