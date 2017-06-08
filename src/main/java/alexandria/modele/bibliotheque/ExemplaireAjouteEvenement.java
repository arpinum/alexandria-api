package alexandria.modele.bibliotheque;

import arpinum.ddd.event.Event;

public class ExemplaireAjouteEvenement extends Event<Bibliotheque> {

    @SuppressWarnings("unused")
    protected ExemplaireAjouteEvenement() {
    }

    public ExemplaireAjouteEvenement(String idBibliotheque, String isbn) {
        super(idBibliotheque);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
    private String isbn;
}
