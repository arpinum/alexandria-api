package fr.arpinum.graine.modele.evenement;

import org.junit.rules.ExternalResource;

@SuppressWarnings("UnusedDeclaration")
public class AvecBusEvenement extends ExternalResource {

    @Override
    protected void before() {
        fauxBus = new FauxBusEvenement();
        LocalisateurBusEvenement.initialise(fauxBus);
    }

    @Override
    protected void after() {
        LocalisateurBusEvenement.initialise(null);
    }

    public FauxBusEvenement bus() {
        return fauxBus;
    }

    private FauxBusEvenement fauxBus;
}
