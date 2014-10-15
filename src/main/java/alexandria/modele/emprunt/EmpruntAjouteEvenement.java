package alexandria.modele.emprunt;

import fr.arpinum.graine.modele.evenement.Evenement;

import java.util.UUID;

public class EmpruntAjouteEvenement implements Evenement{

    public EmpruntAjouteEvenement(UUID emprunt) {
        this.emprunt = emprunt;
    }

    public final UUID emprunt;
}
