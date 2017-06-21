package alexandria.query.exemplaire.resume.recherche

import arpinum.query.WithJongo
import org.junit.Rule
import spock.lang.Specification

class ExemplairesParIsbnCapteurTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    def "retourne les exemplaires"() {
        given:
        jongo.collection("vue_resume_exemplaires") << [
                [_id: UUID.randomUUID(), isbn: 'good'],
                [_id: UUID.randomUUID(), isbn: 'bad']
        ]

        when:
        def result = new ExemplairesParIsbnCapteur().execute(new ExemplairesParIsbn('good'), jongo.jongo())

        then:
        result.size() == 1
        def résumé = result.first()
        résumé.isbn == 'good'
    }
}
