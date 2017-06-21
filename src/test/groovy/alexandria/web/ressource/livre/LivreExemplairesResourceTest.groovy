package alexandria.web.ressource.livre

import arpinum.query.QueryBus
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.collection.Vector
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class LivreExemplairesResourceTest extends Specification {

    def bus = Mock(QueryBus)

    def resource = new LivreExemplairesResource(bus)

    def "retourne les exemplaires"() {
        given:
        def response = Mock(AsyncResponse)
        def result = Vector.of()
        bus.send(_) >> Future.successful(MoreExecutors.newDirectExecutorService(), result)

        when:
        resource.recherche(response, 'isbn')

        then:
        1 * response.resume(result)
    }
}
