package alexandria.web.ressource.lecteur

import alexandria.command.bibliotheque.RendExemplaireCommande
import arpinum.command.CommandBus
import arpinum.infrastructure.Nothing
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.collection.HashMap
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class LecteurEmpruntRessourceTest extends Specification {

    def bus = Mock(CommandBus)

    def ressource = new LecteurEmpruntRessource(bus)

    def "demande à rendre un exemplaire"() {
        given:
        def response = Mock(AsyncResponse)
        def idExemplaire = UUID.randomUUID()

        when:
        ressource.rend(response, idExemplaire, 'id')

        then:
        1 * bus.send({commandeEnvoyé ->
            commandeEnvoyé.idBibliotheque == 'id'
            commandeEnvoyé.idExemplaire == idExemplaire
        } as RendExemplaireCommande) >> Future.successful(MoreExecutors.newDirectExecutorService(), Nothing.INSTANCE)
        1 * response.resume(HashMap.of("status", "ok"))
    }
}
