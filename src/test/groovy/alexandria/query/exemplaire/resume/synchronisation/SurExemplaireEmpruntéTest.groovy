package alexandria.query.exemplaire.resume.synchronisation

import alexandria.modele.bibliotheque.ExemplaireEmprunté
import arpinum.query.WithJongo
import org.junit.Rule
import spock.lang.Specification

class SurExemplaireEmpruntéTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    def capteur = new SurExemplaireEmprunté(jongo.&jongo)

    def "change la disponibilité"() {
        given:
        def évènement = new ExemplaireEmprunté('bibli', UUID.randomUUID(), 'lecteur')
        jongo.collection('vue_resume_exemplaires') << [_id: évènement.idExemplaire, disponible: true]

        when:
        capteur.execute(évènement)

        then:
        def enregistrement = jongo.collection('vue_resume_exemplaires').findOne()
        !enregistrement.disponible
    }
}
