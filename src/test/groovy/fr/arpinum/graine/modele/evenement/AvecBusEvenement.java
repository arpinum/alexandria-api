package fr.arpinum.graine.modele.evenement;

import org.junit.rules.ExternalResource;

@SuppressWarnings("UnusedDeclaration")
public class AvecBusEvenement extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        LocalisateurBusEvenement.initialise(new FauxBusEvenement());
    }

    @Override
    protected void after() {
        LocalisateurBusEvenement.initialise(null);
    }
}
