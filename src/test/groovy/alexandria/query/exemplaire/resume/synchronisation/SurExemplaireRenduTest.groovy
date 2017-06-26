package alexandria.query.exemplaire.resume.synchronisation

import alexandria.modele.bibliotheque.ExemplaireRendu
import arpinum.query.WithJongo
import org.junit.Rule
import spock.lang.Specification

class SurExemplaireRenduTest extends Specification {
    @Rule
    WithJongo jongo = new WithJongo()

    def capteur = new SurExemplaireRendu(jongo.&jongo)

    def "change la disponibilité"() {
        given:
        def évènement = new ExemplaireRendu('bibli', UUID.randomUUID())
        jongo.collection('vue_resume_exemplaires') << [_id: évènement.idExemplaire, disponible: false]

        when:
        capteur.execute(évènement)

        then:
        def enregistrement = jongo.collection('vue_resume_exemplaires').findOne()
        enregistrement.disponible
    }
}
