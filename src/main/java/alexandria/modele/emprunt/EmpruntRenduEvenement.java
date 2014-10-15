package alexandria.modele.emprunt;

import fr.arpinum.graine.modele.evenement.Evenement;

import java.util.UUID;

public class EmpruntRenduEvenement implements Evenement {

    public EmpruntRenduEvenement(UUID emprunt) {
        this.emprunt = emprunt;
    }

    public final UUID emprunt;
}
