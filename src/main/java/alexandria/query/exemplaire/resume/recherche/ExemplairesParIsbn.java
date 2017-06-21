package alexandria.query.exemplaire.resume.recherche;

import alexandria.query.exemplaire.resume.modele.RésuméExemplaire;
import arpinum.query.Query;
import org.hibernate.validator.constraints.NotEmpty;

public class ExemplairesParIsbn extends Query<Iterable<RésuméExemplaire>> {

    public ExemplairesParIsbn(String isbn) {
        this.isbn = isbn;
    }

    @NotEmpty
    public String isbn;
}
