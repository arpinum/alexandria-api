package alexandria.recherche.livre;

import alexandria.modele.bibliotheque.Bibliotheque;

import java.util.UUID;

public class ResumeExemplaire {

    @SuppressWarnings("UnusedDeclaration")
    public ResumeExemplaire() {
    }

    public ResumeExemplaire(Bibliotheque bibliotheque) {
        this.emailLecteur = bibliotheque.emailLecteur();
        this.idBibliotheque = bibliotheque.getId();
    }

    public String emailLecteur;
    public UUID idBibliotheque;
    public boolean disponible = true;
}
