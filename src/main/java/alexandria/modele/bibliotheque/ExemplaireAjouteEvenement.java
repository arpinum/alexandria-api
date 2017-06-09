package alexandria.modele.bibliotheque;

import arpinum.ddd.event.Event;

public class ExemplaireAjouteEvenement extends Event<Bibliotheque> {

    @SuppressWarnings("unused")
    protected ExemplaireAjouteEvenement() {
    }

    public ExemplaireAjouteEvenement(String idBibliotheque, String idLecteur, String isbn) {
        super(idBibliotheque);
        this.idLecteur = idLecteur;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIdLecteur() {
        return idLecteur;
    }

    private String idLecteur;
    private String isbn;
}
