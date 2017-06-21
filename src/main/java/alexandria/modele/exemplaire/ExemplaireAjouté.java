package alexandria.modele.exemplaire;

import arpinum.ddd.event.Event;

import java.util.UUID;

public class ExemplaireAjouté extends Event<Exemplaire> {

    @SuppressWarnings("unused")
    protected ExemplaireAjouté() {
    }

    public ExemplaireAjouté(UUID idExemplaire, String idBibliothèque, String idLecteur, String isbn) {
        super(idExemplaire);
        this.idBibliothèque = idBibliothèque;
        this.idLecteur = idLecteur;
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getIdLecteur() {
        return idLecteur;
    }

    public String getIdBibliothèque() {
        return idBibliothèque;
    }

    private String idLecteur;
    private String isbn;
    private String idBibliothèque;
}
