package fr.arpinum.graine.modele.evenement;

import fr.arpinum.graine.infrastructure.bus.HandlerMessage;


@SuppressWarnings("UnusedDeclaration")
public interface HandlerEvenement<TEvenement extends Evenement> extends HandlerMessage<TEvenement, Void> {
}
