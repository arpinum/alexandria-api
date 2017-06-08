package alexandria.query.livre.resume.modele;

import org.jongo.marshall.jackson.oid.Id;


public class ResumeLivre {

    public ResumeLivre() {
    }

    public ResumeLivre(String isbn, String titre) {
        this.isbn = isbn;
        this.titre = titre;
        this.nombre = 1;
    }

    @Id
    public String isbn;
    public String titre;
    public int nombre;

}
