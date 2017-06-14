package authentification.application

import arpinum.infrastructure.security.JwtBuilder
import authentification.infrastructure.dao.mémoire.UtilisateurDaoMémoire
import authentification.modele.Utilisateur
import io.vavr.collection.HashMap
import spock.lang.Specification

class UtilisateurServiceTest extends Specification {

    def dao = new UtilisateurDaoMémoire()

    def builder = Mock(JwtBuilder)

    def service = new UtilisateurService(dao, builder)

    def "fabrique un token valide"() {
        given:
        def utilisateur = Utilisateur.crée("email@email", "jb", "dusse")
        dao.ajoute(utilisateur)

        when:
        def token = service.authentifie('email@email', 'jb', 'dusse')

        then:
        token == 'token'
        1 * builder.build(utilisateur.id, HashMap.of('prenom', 'jb', 'nom', 'dusse')) >> 'token'
    }
}
