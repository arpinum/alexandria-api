package alexandria.modele.bibliotheque;


public class IdentifiantExemplaire {

    public IdentifiantExemplaire(String idBibliothèque, String isbn) {
        this.idBibliothèque = idBibliothèque;
        this.isbn = isbn;
    }

    public final String idBibliothèque;
    public final String isbn;
}
