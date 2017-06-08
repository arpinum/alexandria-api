package alexandria.infrastructure.persistance.eventsource;

import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import arpinum.ddd.event.EventStore;
import arpinum.infrastructure.persistance.eventsourcing.EventSourcedRepository;


public class EntrepotBibliothequesEventSourced extends EventSourcedRepository<String, Bibliotheque> implements EntrepotBibliotheques {

    public EntrepotBibliothequesEventSourced(EventStore store) {
        super(store);
    }

}
