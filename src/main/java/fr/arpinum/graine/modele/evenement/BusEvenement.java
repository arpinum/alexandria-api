package fr.arpinum.graine.modele.evenement;


@SuppressWarnings("UnusedDeclaration")
public interface BusEvenement {

    public void publie(Evenement evenement);

    public static BusEvenement INSTANCE() {
        return LocalisateurBusEvenement.INSTANCE();
    }
}
