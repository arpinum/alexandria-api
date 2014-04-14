package alexandria.recherche.livre;

import org.jongo.marshall.jackson.oid.Id;

public class ResumeLivre {

    @Id
    public String isbn;
    public String titre;
    public int nombre;

}
