package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLinkAvecUuid;
import org.mongolink.MongoSession;

public class EntrepotBibliothequesMongolink extends EntrepotMongoLinkAvecUuid<Bibliotheque> implements EntrepotBibliotheques {
    protected EntrepotBibliothequesMongolink(MongoSession session) {
        super(session);
    }
}
