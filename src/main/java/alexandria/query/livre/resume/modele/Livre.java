package alexandria.query.livre.resume.modele;

import org.jongo.marshall.jackson.oid.MongoId;

public class Livre {

    @SuppressWarnings("UnusedDeclaration")
    public Livre() {
    }

    @MongoId
    public String isbn;

    public String titre;

    public String image;

    public String description;

    public int nombre = 1;
}
