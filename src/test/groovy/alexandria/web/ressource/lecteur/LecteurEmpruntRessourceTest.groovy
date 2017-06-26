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

    def "demande à rendre l'exemplaire"() {
        given:
        def response = Mock(AsyncResponse)
        def bibliotheque = 'id'
        def exemplaire = UUID.randomUUID()

        when:
        ressource.rend(response, bibliotheque, exemplaire)

        then:
        1 * bus.send({ commande ->
            commande.idBibliothèque == bibliotheque
            commande.idExemplaire == exemplaire
        } as RendExemplaireCommande) >> Future.successful(MoreExecutors.newDirectExecutorService(), Nothing.INSTANCE)
        1 * response.resume(HashMap.of("status", "youpi"))
    }
}
