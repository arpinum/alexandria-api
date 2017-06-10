package authentification.dao;


import authentification.modele.Utilisateur;
import io.vavr.control.Option;

public interface UtilisateurDao {

    Option<Utilisateur> parId(String id);

    void ajoute(Utilisateur utilisateur);
}
