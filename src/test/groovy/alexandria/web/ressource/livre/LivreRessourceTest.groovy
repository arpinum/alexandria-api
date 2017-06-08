package alexandria.web.ressource.livre

import alexandria.query.livre.details.modele.Livre
import alexandria.query.livre.details.recherche.RechercheDetailsLivre
import arpinum.query.QueryBus
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class LivreRessourceTest extends Specification {

    def bus = Mock(QueryBus)

    LivreRessource ressource

    void setup() {
        ressource = new LivreRessource(bus)
    }

    def "peut retourner le livre"() {
        given:
        def response = Mock(AsyncResponse)
        def livre = new Livre()
        bus.send(_ as RechercheDetailsLivre) >> Future.successful(livre)

        when:
        ressource.recherche(response, "isbn")

        then:
        1 * response.resume(livre)
    }
}
