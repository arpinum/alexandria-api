package alexandria.modele.lecteur;

import fr.arpinum.graine.modele.Racine;

public class Lecteur implements Racine<String>{

    public String getEmail() {
        return email;
    }

    public Lecteur(String email) {
        this.email = email;
    }

    @Override
    public String getId() {
        return email;
    }

    private String email;

}
