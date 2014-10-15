package alexandria.modele.lecteur;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.*;
import fr.arpinum.graine.modele.Racine;
import fr.arpinum.graine.modele.evenement.BusEvenement;

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

    public Emprunt emprunte(Bibliotheque bibliotheque, String isbn){
        final Exemplaire exemplaire = bibliotheque.trouve(isbn).orElseThrow(ExemplaireInconnu::new);
        if(LocalisateurEntrepots.emprunts().existePour(exemplaire)) {
            throw new ExemplaireDejaEmprunte();
        }
        final Emprunt emprunt = new Emprunt(this, exemplaire);
        BusEvenement.INSTANCE().publie(new EmpruntAjouteEvenement(emprunt.getId()));
        return emprunt;
    }


    private String email;
}
