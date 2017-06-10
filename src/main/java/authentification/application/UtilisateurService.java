package authentification.application;


import authentification.dao.UtilisateurDao;
import authentification.modele.IdUtilisateur;
import authentification.modele.Utilisateur;
import io.vavr.control.Option;
import org.pac4j.core.profile.definition.CommonProfileDefinition;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.jwt.profile.JwtProfile;

import javax.inject.Inject;

public class UtilisateurService {

    @Inject
    public UtilisateurService(UtilisateurDao utilisateurDao, JwtGenerator<JwtProfile> generator) {
        this.utilisateurDao = utilisateurDao;
        this.generator = generator;
    }

    public String authentifie(String email, String prénom, String nom) {
        return utilisateurDao.parId(IdUtilisateur.depuisEmail(email))
                .orElse(() -> crée(email, prénom, nom))
                .map(u -> {
                    JwtProfile jwtProfile = new JwtProfile();
                    jwtProfile.setId(u.getId());
                    jwtProfile.addAttribute(CommonProfileDefinition.FIRST_NAME, u.getPrénom());
                    jwtProfile.addAttribute(CommonProfileDefinition.FAMILY_NAME, u.getNom());

                    return jwtProfile;
                })
                .map(p -> generator.generate(p))
                .get();
    }

    private Option<Utilisateur> crée(String email, String prénom, String nom) {
        Utilisateur utilisateur = Utilisateur.crée(email, prénom, nom);
        utilisateurDao.ajoute(utilisateur);
        return Option.of(utilisateur);
    }

    private UtilisateurDao utilisateurDao;
    private JwtGenerator<JwtProfile> generator;
}
