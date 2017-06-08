package alexandria.query.livre.details.modele;

import catalogue.DetailsLivre;
import com.google.common.collect.Lists;
import org.jongo.marshall.jackson.oid.Id;

import java.util.List;

public class Livre {

    @SuppressWarnings("UnusedDeclaration")
    public Livre() {
    }

    public Livre(String isbn, DetailsLivre detailsLivre) {
        this.isbn = isbn;
        this.titre = detailsLivre.titre;
        this.image = detailsLivre.image;
    }

    @Id
    public String isbn;

    public String titre;

    public String image;

    public List<ResumeExemplaire> exemplaires =Lists.newArrayList();
}
