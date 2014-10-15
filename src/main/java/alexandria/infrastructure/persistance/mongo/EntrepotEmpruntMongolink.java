package alexandria.infrastructure.persistance.mongo;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EntrepotEmprunts;
import fr.arpinum.graine.infrastructure.persistance.mongo.EntrepotMongoLinkAvecUuid;
import org.jongo.Jongo;
import org.mongolink.MongoSession;

public class EntrepotEmpruntMongolink extends EntrepotMongoLinkAvecUuid<Emprunt> implements EntrepotEmprunts{

    protected EntrepotEmpruntMongolink(MongoSession session) {
        super(session);
    }

    @Override
    public boolean existePour(Exemplaire exemplaire) {
        final Jongo jongo = new Jongo(getSession().getDb());
        final long compte = jongo.getCollection("emprunt")
                .count("{'exemplaire.isbn':#, 'exemplaire.identifiantBibliotheque':#}", exemplaire.isbn(), exemplaire.identifiantBibliotheque());
        return compte >= 1;

    }
}
