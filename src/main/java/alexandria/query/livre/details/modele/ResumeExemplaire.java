package alexandria.query.livre.details.modele;

import alexandria.modele.bibliotheque.Bibliotheque;

public class ResumeExemplaire {

    @SuppressWarnings("UnusedDeclaration")
    public ResumeExemplaire() {
    }

    public ResumeExemplaire(String idLecteur, String idBibliotheque) {
        this.emailLecteur = idLecteur;
        this.idBibliotheque = idBibliotheque;
    }

    public String emailLecteur;
    public String idBibliotheque;
    public boolean disponible = true;
}
