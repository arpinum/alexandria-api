package alexandria.infrastructure.persistance.eventsource;


import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.exemplaire.EntrepotExemplaires;
import arpinum.ddd.event.EventStore;

import javax.inject.Inject;

public class LocalisateurEntrepotsEventSource extends LocalisateurEntrepots {

    @Inject
    public LocalisateurEntrepotsEventSource(EventStore store) {
        entrepotBibliotheques = new EntrepotBibliothequesEventSourced(store);
        exemplaires = new EntrepotExemplaireEventSourced(store);
    }

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return entrepotBibliotheques;
    }

    @Override
    protected EntrepotExemplaires getExemplaires() {
        return exemplaires;
    }

    private final EntrepotExemplaireEventSourced exemplaires;
    private final EntrepotBibliothequesEventSourced entrepotBibliotheques;
}
