package alexandria.modele.lecteur;


public class FicheLecteur {

    @SuppressWarnings("unused")
    public FicheLecteur() {
    }

    public FicheLecteur(String id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    private String id;
    private String nom;
    private String prenom;
}
