package alexandria.recherche.livre;

import fr.arpinum.graine.recherche.Recherche;

public class RechercheDetailsLivre extends Recherche<Livre> {

    public RechercheDetailsLivre(String isbn) {
        this.isbn = isbn;
    }

    public final String isbn;
}
