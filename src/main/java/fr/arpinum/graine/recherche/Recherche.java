package fr.arpinum.graine.recherche;

import fr.arpinum.graine.infrastructure.bus.Message;

@SuppressWarnings("UnusedDeclaration")
public class Recherche<TReponse> implements Message<TReponse> {

    public Recherche<TReponse> avecLimite(int limite) {
        this.limite = limite;
        return this;
    }

    public Recherche<TReponse> passe(int passe) {
        this.passe = passe;
        return this;
    }

    private int limite;
    private int passe;
}
