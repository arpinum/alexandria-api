package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EntrepotEmprunts;
import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLinkAvecUuid;
import org.jongo.Jongo;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.Criteria;
import org.mongolink.domain.criteria.Restrictions;

import java.util.Optional;

public class EntrepotEmpruntMongolink extends EntrepotMongoLinkAvecUuid<Emprunt> implements EntrepotEmprunts{

    protected EntrepotEmpruntMongolink(MongoSession session) {
        super(session);
    }

    @Override
    public boolean existePour(Exemplaire exemplaire) {
        final Jongo jongo = new Jongo(getSession().getDb());
        final long compte = jongo.getCollection("emprunt")
                .count("{'exemplaire.isbn':#, 'exemplaire.identifiantBibliotheque':#, dateRemise: {$exists:false}}", exemplaire.isbn(), exemplaire.identifiantBibliotheque());
        return compte >= 1;
    }

    @Override
    public Optional<Emprunt> empruntCourantDe(Exemplaire exemplaire) {
        final Criteria criteria = getSession().createCriteria(Emprunt.class);
        criteria.add(Restrictions.equals("exemplaire.isbn", exemplaire.isbn()));
        criteria.add(Restrictions.equals("exemplaire.identifiantBibliotheque", exemplaire.identifiantBibliotheque()));
        criteria.add(Restrictions.exists("dateRemise", false));
        return criteria.list().stream().findFirst();
    }
}
