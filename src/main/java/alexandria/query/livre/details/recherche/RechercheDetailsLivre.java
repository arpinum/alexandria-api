package alexandria.query.livre.details.recherche;

import alexandria.query.livre.details.modele.Livre;
import arpinum.query.Query;

public class RechercheDetailsLivre extends Query<Livre> {

    public RechercheDetailsLivre(String isbn) {
        this.isbn = isbn;
    }

    public final String isbn;
}
