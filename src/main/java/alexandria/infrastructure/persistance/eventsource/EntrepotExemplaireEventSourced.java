package alexandria.infrastructure.persistance.eventsource;

import alexandria.modele.exemplaire.*;
import arpinum.ddd.event.EventStore;
import arpinum.infrastructure.persistance.eventsourcing.EventSourcedRepositoryWithUuid;

public class EntrepotExemplaireEventSourced extends EventSourcedRepositoryWithUuid<Exemplaire> implements EntrepotExemplaires {

    public EntrepotExemplaireEventSourced(EventStore eventStore) {
        super(eventStore);
    }
}
