package fr.arpinum.graine.recherche;

import fr.arpinum.graine.infrastructure.bus.HandlerMessage;

public interface HandlerRecherche<TRecherche extends Recherche<TReponse>, TReponse> extends HandlerMessage<TRecherche, TReponse> {
}
