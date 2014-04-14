package alexandria.recherche.livre

import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class HandlerRechercheTousLesLivresTest extends Specification {

    @Rule
    public AvecJongo jongo = new AvecJongo()


    def "peut retourner un livre"() {
        given:
        jongo.collection("vue_resumelivre") << [_id: "isbn", titre: "Harry Potter", nombre: 2]

        when:
        def resultats = new HandlerRechercheTousLesLivres().execute(new TousLesLivres(), jongo.jongo())

        then:
        def livre = resultats.first()
        livre != null
        livre.isbn == "isbn"
        livre.titre == "Harry Potter"
        livre.nombre == 2
    }
}
