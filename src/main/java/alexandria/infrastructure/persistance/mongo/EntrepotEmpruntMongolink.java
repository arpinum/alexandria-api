package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EntrepotEmprunts;
import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLinkAvecUuid;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.List;

public class EntrepotEmpruntMongolink extends EntrepotMongoLinkAvecUuid<Emprunt> implements EntrepotEmprunts{

    protected EntrepotEmpruntMongolink(MongoSession session) {
        super(session);
    }

    @Override
    public boolean existePour(Exemplaire exemplaire) {
        final Criteria criteria = getSession().createCriteria(Emprunt.class);
        criteria.add(Restrictions.equals("exemplaire.isbn", exemplaire.isbn()));
        criteria.add(Restrictions.equals("exemplaire.identifiantBibliotheque", exemplaire.identifiantBibliotheque()));
        final List<Exemplaire> résultat = criteria.list();
        return résultat.size() >= 1;
    }
}
