package alexandria.infrastructure.persistance.eventsource;


import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import arpinum.ddd.event.EventStore;

import javax.inject.Inject;

public class LocalisateurEntrepotsEventSource extends LocalisateurEntrepots {

    private final EntrepotBibliothequesEventSourced entrepotBibliotheques;

    @Inject
    public LocalisateurEntrepotsEventSource(EventStore store) {
        entrepotBibliotheques = new EntrepotBibliothequesEventSourced(store);
    }

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return entrepotBibliotheques;
    }
}
