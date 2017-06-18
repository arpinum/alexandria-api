package alexandria.query.livre.resume.modele;

import alexandria.query.livre.details.modele.ResumeExemplaire;
import catalogue.DetailsLivre;
import com.google.common.collect.Lists;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.List;

public class Livre {

    @SuppressWarnings("UnusedDeclaration")
    public Livre() {
    }

    public Livre(String isbn, DetailsLivre detailsLivre) {
        this.isbn = isbn;
        this.titre = detailsLivre.titre;
        this.description = detailsLivre.description;
        this.image = detailsLivre.image;
    }

    @MongoId
    public String isbn;

    public String titre;

    public String image;

    public String description;

    public int nombre = 1;
}
