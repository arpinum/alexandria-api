package fr.arpinum.graine.commande;


import fr.arpinum.graine.infrastructure.bus.HandlerMessage;

public interface HandlerCommande<TCommande extends Commande<TReponse>, TReponse> extends HandlerMessage<TCommande, TReponse> {
}
