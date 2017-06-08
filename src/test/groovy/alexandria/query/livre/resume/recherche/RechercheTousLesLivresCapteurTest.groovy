package alexandria.query.livre.resume.recherche

import arpinum.query.WithJongo
import org.junit.Rule
import spock.lang.Specification

class RechercheTousLesLivresCapteurTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()


    def "peut retourner un livre"() {
        given:
        jongo.collection("vue_resumelivre") << [_id: "isbn", titre: "Harry Potter", nombre: 2]

        when:
        def resultats = new RechercheTousLesLivresCapteur().execute(new TousLesLivres(), jongo.jongo())

        then:
        def livre = resultats.first()
        livre != null
        livre.isbn == "isbn"
        livre.titre == "Harry Potter"
        livre.nombre == 2
    }
}
