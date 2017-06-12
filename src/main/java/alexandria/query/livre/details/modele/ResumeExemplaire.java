package alexandria.query.livre.details.modele;

import alexandria.modele.lecteur.FicheLecteur;
import alexandria.modele.lecteur.Lecteur;

public class ResumeExemplaire {

    @SuppressWarnings("UnusedDeclaration")
    public ResumeExemplaire() {
    }

    public ResumeExemplaire(FicheLecteur lecteur, String idBibliotheque) {
        this.lecteur = lecteur;
        this.idBibliotheque = idBibliotheque;
    }

    public FicheLecteur lecteur;
    public String idBibliotheque;
    public boolean disponible = true;
}
