package alexandria.modele.lecteur;


public class FicheLecteur {

    @SuppressWarnings("unused")
    public FicheLecteur() {
    }

    public FicheLecteur(String id, String nom, String prénom) {
        this.id = id;
        this.nom = nom;
        this.prénom = prénom;
    }

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrénom() {
        return prénom;
    }

    private String id;
    private String nom;
    private String prénom;
}
