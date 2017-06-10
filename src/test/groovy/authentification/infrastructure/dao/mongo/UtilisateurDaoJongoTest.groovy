package authentification.infrastructure.dao.mongo

import arpinum.query.WithJongo
import authentification.modele.Utilisateur
import com.mongodb.DBObject
import io.vavr.control.Option
import org.junit.Rule
import spock.lang.Specification

class UtilisateurDaoJongoTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    UtilisateurDaoJongo dao

    void setup() {
        dao = new UtilisateurDaoJongo(jongo.jongo())
    }

    def "retourne vide si absent"() {
        when:
        def résultat = dao.parId('id')

        then:
        résultat.isEmpty()
    }

    def "retourne l'utilisateur si présent"() {
        given:
        jongo.collection("utilisateur") << [_id:'id', nom:'nom', prénom: 'prénom', email:'jb@email.com']

        when:
        def résultat = dao.parId('id')

        then:
        résultat.isDefined()
        def utilisateur = résultat.get()
        utilisateur.id == 'id'
        utilisateur.prénom == 'prénom'
        utilisateur.nom == 'nom'
        utilisateur.email == 'jb@email.com'
    }

    def "peut ajouter un utilisateur"() {
        given:
        def utilisateur = new Utilisateur(id: 'id', nom: 'nom', prénom: 'prénom', email: 'jb@email.com')

        when:
        dao.ajoute(utilisateur)

        then:
        def record = jongo.collection('utilisateur').findOne()
        record._id == 'id'
        record.nom == 'nom'
        record.prénom == 'prénom'
        record.email == 'jb@email.com'
    }
}
