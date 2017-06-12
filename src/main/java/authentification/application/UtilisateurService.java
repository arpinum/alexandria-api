package authentification.application;


import arpinum.infrastructure.security.JwtBuilder;
import authentification.dao.UtilisateurDao;
import authentification.modele.IdUtilisateur;
import authentification.modele.Utilisateur;
import io.vavr.collection.HashMap;
import io.vavr.control.Option;

import javax.inject.Inject;

public class UtilisateurService {

    @Inject
    public UtilisateurService(UtilisateurDao utilisateurDao, JwtBuilder jwtBuilder) {
        this.utilisateurDao = utilisateurDao;
        this.jwtBuilder = jwtBuilder;
    }

    public String authentifie(String email, String prénom, String nom) {
        return utilisateurDao.parId(IdUtilisateur.depuisEmail(email))
                .orElse(() -> crée(email, prénom, nom))
                .map(u -> jwtBuilder.build(u.getId(), HashMap.of("firstName", u.getPrénom(), "lastName", u.getNom())))
                .get();
    }

    private Option<Utilisateur> crée(String email, String prénom, String nom) {
        Utilisateur utilisateur = Utilisateur.crée(email, prénom, nom);
        utilisateurDao.ajoute(utilisateur);
        return Option.of(utilisateur);
    }

    public Option<Utilisateur> trouve(String id) {
        return utilisateurDao.parId(id);
    }

    private UtilisateurDao utilisateurDao;
    private JwtBuilder jwtBuilder;
}
