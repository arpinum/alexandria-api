package alexandria.recherche.livre;

public class ResumeExemplaire {

    @SuppressWarnings("UnusedDeclaration")
    public ResumeExemplaire() {
    }

    public ResumeExemplaire(String emailLecteur) {
        this.emailLecteur = emailLecteur;
    }

    public String emailLecteur;
    public boolean disponible = true;
}
