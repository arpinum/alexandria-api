package fr.arpinum.graine.modele.evenement;

public final class LocalisateurBusEvenement {

    private LocalisateurBusEvenement() {
    }

    public static BusEvenement INSTANCE() {
        return instance;
    }

    public static void initialise(BusEvenement instance) {
        LocalisateurBusEvenement.instance = instance;
    }

    private static BusEvenement instance;
}
