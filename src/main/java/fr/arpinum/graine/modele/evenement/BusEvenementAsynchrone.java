package fr.arpinum.graine.modele.evenement;

import com.google.common.collect.Lists;
import fr.arpinum.graine.commande.SynchronisationCommande;
import fr.arpinum.graine.infrastructure.bus.BusAsynchrone;

import javax.inject.Inject;
import java.util.Queue;
import java.util.Set;

@SuppressWarnings("UnusedDeclaration")
public class BusEvenementAsynchrone extends BusAsynchrone implements BusEvenement, SynchronisationCommande {

    @Inject
    public BusEvenementAsynchrone(Set<SynchronisationEvenement> synchronisations, Set<CapteurEvenement> handlers) {
        super(synchronisations, handlers);
    }

    @Override
    public void apresExecution() {
        LOGGER.debug("Propagation des évènements");
        while (!évènements.get().isEmpty()) {
            final Evenement evenement = évènements.get().poll();
            if (estSynchrone(evenement)) {
                envoieEtAttendReponse(evenement);
            } else {
                envoie(evenement);
            }
        }
    }

    private boolean estSynchrone(Evenement evenement) {
        return evenement.getClass().getAnnotation(Synchrone.class) != null;

    }

    @Override
    public void surErreur() {
        évènements.get().clear();
    }

    @Override
    public void publie(Evenement evenement) {
        évènements.get().add(evenement);
    }

    private final ThreadLocal<Queue<Evenement>> évènements = ThreadLocal.withInitial(Lists::newLinkedList);
}