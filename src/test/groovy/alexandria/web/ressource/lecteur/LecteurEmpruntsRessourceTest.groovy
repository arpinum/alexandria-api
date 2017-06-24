package alexandria.web.ressource.lecteur

import alexandria.modele.bibliotheque.Emprunt
import alexandria.saga.bibliiotheque.SortExemplaireSaga
import arpinum.command.CommandBus
import arpinum.web.SecurityContextBuilder
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class LecteurEmpruntsRessourceTest extends Specification {

    def bus = Mock(CommandBus)

    def ressource = new LecteurEmpruntsRessource(bus)

    def "demande la sortie de l'exemplaire pour le lecteur connecté"() {
        given:
        def saga = new SortExemplaireSaga()
        def emprunt = new Emprunt('', UUID.randomUUID(), '')
        def response = Mock(AsyncResponse)

        when:
        ressource.sort(response, SecurityContextBuilder.forId('lecteur'), saga)

        then:
        1 * bus.send({ payload ->
            payload == saga
            payload.idLecteur == 'lecteur'
        }) >> Future.successful(MoreExecutors.newDirectExecutorService(), emprunt)
        1 * response.resume(emprunt)
    }
}
