package alexandria.recherche.construction;

import fr.arpinum.graine.modele.evenement.HandlerEvenement;
import alexandria.modele.FauxEvenement;

public class SurFauxEvenement implements HandlerEvenement<FauxEvenement> {

    @Override
    public Void execute(FauxEvenement fauxEvenement) {
        return null;
    }
}
