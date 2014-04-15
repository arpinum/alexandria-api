package fr.arpinum.graine.modele.evenement;

import fr.arpinum.graine.infrastructure.bus.HandlerMessage;


@SuppressWarnings("UnusedDeclaration")
public interface HandlerEvenement<TEvenement extends Evenement> extends HandlerMessage<TEvenement, Void> {

    public void executeEvenement(TEvenement evenement);

    default Void execute(TEvenement evenement) {
        executeEvenement(evenement);
        return null;
    }
}
