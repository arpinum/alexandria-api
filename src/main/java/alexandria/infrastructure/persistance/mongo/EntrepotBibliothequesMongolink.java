package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLinkAvecUuid;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.List;
import java.util.Optional;

public class EntrepotBibliothequesMongolink extends EntrepotMongoLinkAvecUuid<Bibliotheque> implements EntrepotBibliotheques {
    protected EntrepotBibliothequesMongolink(MongoSession session) {
        super(session);
    }

    @Override
    public Optional<Bibliotheque> parEmailLecteur(String email) {
        final Criteria criteria = criteria();
        criteria.add(Restrictions.equals("emailLecteur", email));
        List<Bibliotheque> liste = criteria.list();
        return liste.stream().findFirst();
    }

}
