package alexandria.web.ressource.livre

import alexandria.query.livre.resume.modele.Livre
import arpinum.query.QueryBus
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class LivresRessourceTest extends Specification {

    def bus = Mock(QueryBus)
    LivresRessource ressource

    void setup() {
        ressource = new LivresRessource(bus)
    }

    def "retourne bien tous les livres"() {
        given:
        def livres = [new Livre()]
        bus.send(_) >> Future.successful(livres)
        def response = Mock(AsyncResponse)

        when:
        ressource.recherche(response)

        then:
        1 * response.resume(livres)
    }
}
