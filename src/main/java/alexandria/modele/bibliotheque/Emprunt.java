package alexandria.modele.bibliotheque;

import java.util.UUID;

public class Emprunt {

    public Emprunt(String idBibliothèque, UUID idExemplaire, String idLecteur) {
        this.idBibliothèque = idBibliothèque;
        this.idExemplaire = idExemplaire;
        this.idLecteur = idLecteur;
    }

    public final String idBibliothèque;
    public final UUID idExemplaire;
    public final String idLecteur;
}
