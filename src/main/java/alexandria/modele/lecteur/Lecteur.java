package alexandria.modele.lecteur;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
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

    public Emprunt emprunte(Exemplaire exemplaire) {
        return new Emprunt(exemplaire, email);
    }

    private String email;
}
