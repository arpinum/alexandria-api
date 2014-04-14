package fr.arpinum.graine.commande;


import fr.arpinum.graine.infrastructure.bus.Message;

public interface Commande<TReponse> extends Message<TReponse> {
}
