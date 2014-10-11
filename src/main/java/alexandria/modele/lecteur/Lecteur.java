package alexandria.modele.lecteur;

import fr.arpinum.graine.modele.Racine;

public class Lecteur implements Racine<String>{

    public Lecteur(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getId() {
        return email;
    }


    private String email;
}
