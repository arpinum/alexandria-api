package fr.arpinum.graine.commande;


import fr.arpinum.graine.infrastructure.bus.CapteurMessage;

public interface CapteurCommande<TCommande extends Commande<TReponse>, TReponse> extends CapteurMessage<TCommande, TReponse> {
}
