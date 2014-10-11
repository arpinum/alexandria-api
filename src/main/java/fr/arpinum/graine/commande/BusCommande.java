package fr.arpinum.graine.commande;


import fr.arpinum.graine.infrastructure.bus.BusAsynchrone;

import javax.inject.Inject;
import java.util.Set;


@SuppressWarnings("UnusedDeclaration")
public class BusCommande extends BusAsynchrone {

    @Inject
    public BusCommande(Set<SynchronisationCommande> synchronisations, Set<CapteurCommande> handlers) {
        super(synchronisations, handlers);
    }
}


