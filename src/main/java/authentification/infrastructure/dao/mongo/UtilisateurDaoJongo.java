package authentification.infrastructure.dao.mongo;


import authentification.dao.UtilisateurDao;
import authentification.modele.Utilisateur;
import io.vavr.control.Option;
import org.jongo.Jongo;

import javax.inject.Inject;

public class UtilisateurDaoJongo implements UtilisateurDao {

    @Inject
    public UtilisateurDaoJongo(Jongo jongo) {
        this.jongo = jongo;
    }

    @Override
    public Option<Utilisateur> parId(String id) {
        return Option.of(jongo.getCollection(COLLECTION)
                .findOne("{_id:#}", id).as(Utilisateur.class));
    }

    @Override
    public void ajoute(Utilisateur utilisateur) {
        jongo.getCollection(COLLECTION).save(utilisateur);
    }

    private Jongo jongo;
    private static final String COLLECTION = "utilisateur";
}
