package fr.arpinum.graine.modele.evenement;

import fr.arpinum.graine.infrastructure.bus.CapteurMessage;


@SuppressWarnings("UnusedDeclaration")
public interface CapteurEvenement<TEvenement extends Evenement> extends CapteurMessage<TEvenement, Void> {

    public void executeEvenement(TEvenement evenement);

    default Void execute(TEvenement evenement) {
        executeEvenement(evenement);
        return null;
    }
}
