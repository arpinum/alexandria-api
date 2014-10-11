package fr.arpinum.graine.recherche;

import fr.arpinum.graine.infrastructure.bus.CapteurMessage;

public interface CapteurRecherche<TRecherche extends Recherche<TReponse>, TReponse> extends CapteurMessage<TRecherche, TReponse> {
}
