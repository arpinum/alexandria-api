package authentification.infrastructure.dao.mémoire;


import authentification.dao.UtilisateurDao;
import authentification.modele.Utilisateur;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;

public class UtilisateurDaoMémoire implements UtilisateurDao{

    @Override
    public Option<Utilisateur> parId(String id) {
        return utilisateurs.get(id);
    }

    @Override
    public void ajoute(Utilisateur utilisateur) {
        utilisateurs = utilisateurs.put(utilisateur.getId(), utilisateur);
    }

    private Map<String, Utilisateur> utilisateurs = HashMap.empty();
}
