package alexandria.query.livre.details.modele;

import alexandria.modele.bibliotheque.Bibliotheque;

public class ResumeExemplaire {

    @SuppressWarnings("UnusedDeclaration")
    public ResumeExemplaire() {
    }

    public ResumeExemplaire(Bibliotheque bibliotheque) {
        this.emailLecteur = bibliotheque.idLecteur();
        this.idBibliotheque = bibliotheque.getId();
    }

    public String emailLecteur;
    public String idBibliotheque;
    public boolean disponible = true;
}
