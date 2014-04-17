package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EntrepotEmprunt;
import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLinkAvecUuid;
import org.mongolink.MongoSession;


public class EntrepotEmpruntsMongoLink extends EntrepotMongoLinkAvecUuid<Emprunt> implements EntrepotEmprunt {
    protected EntrepotEmpruntsMongoLink(MongoSession session) {
        super(session);
    }
}
