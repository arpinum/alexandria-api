package fr.arpinum.graine.recherche;

import com.google.common.collect.Sets;
import fr.arpinum.graine.infrastructure.bus.BusAsynchrone;

import javax.inject.Inject;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
public class BusRecherche extends BusAsynchrone {

    @Inject
    public BusRecherche(Set<CapteurRecherche> handlers) {
        super(Sets.newHashSet(), handlers);
    }

}
