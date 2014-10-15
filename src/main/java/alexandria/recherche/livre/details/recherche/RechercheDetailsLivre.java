package alexandria.recherche.livre.details.recherche;

import alexandria.recherche.livre.details.modele.Livre;
import fr.arpinum.graine.recherche.Recherche;

public class RechercheDetailsLivre extends Recherche<Livre> {

    public RechercheDetailsLivre(String isbn) {
        this.isbn = isbn;
    }

    public final String isbn;
}
