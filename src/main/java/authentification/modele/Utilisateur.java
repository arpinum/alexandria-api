package authentification.modele;


import org.jongo.marshall.jackson.oid.MongoId;

public class Utilisateur {

    public static Utilisateur crée(String email, String prénom, String nom) {
        Utilisateur résultat = new Utilisateur();
        résultat.id = IdUtilisateur.depuisEmail(email);
        résultat.prénom = prénom;
        résultat.nom = nom;
        résultat.email = email;
        return résultat;
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

    public String getEmail() {
        return email;
    }

    @MongoId
    private String id;
    private String nom;
    private String prénom;
    private String email;
}
