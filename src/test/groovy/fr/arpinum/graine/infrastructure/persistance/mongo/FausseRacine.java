package fr.arpinum.graine.infrastructure.persistance.mongo;

import fr.arpinum.graine.modele.Racine;

public class FausseRacine implements Racine<String> {

    @SuppressWarnings("UnusedDeclaration")
    public FausseRacine() {
    }

    FausseRacine(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public String id;
}