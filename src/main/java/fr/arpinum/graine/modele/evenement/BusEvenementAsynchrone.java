package fr.arpinum.graine.modele.evenement;

import fr.arpinum.graine.infrastructure.bus.BusAsynchrone;

import javax.inject.Inject;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
public class BusEvenementAsynchrone extends BusAsynchrone implements BusEvenement {

    @Inject
    public BusEvenementAsynchrone(Set<SynchronisationEvenement> synchronisations, Set<CapteurEvenement> handlers) {
        super(synchronisations, handlers);
    }

    @Override
    public void publie(Evenement evenement) {
        envoie(evenement);
    }
}
