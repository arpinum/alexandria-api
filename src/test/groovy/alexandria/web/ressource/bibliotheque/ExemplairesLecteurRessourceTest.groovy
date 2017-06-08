package alexandria.web.ressource.bibliotheque

import alexandria.command.bibliotheque.AjoutExemplaireCommande
import arpinum.command.CommandBus
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class ExemplairesLecteurRessourceTest extends Specification {

    def bus = Mock(CommandBus)

    def "peut demander à créer un exemplaire"() {
        given:
        def ressource = new ExemplairesLecteurRessource(bus)
        def response = Mock(AsyncResponse)
        when:
        ressource.ajoute(response, "monmail", "iiiiisbn")

        then:
        1 * bus.send({
            it.idBibliotheque == "monmail" && it.isbn == "iiiiisbn"
        }) >> Future.successful(UUID.randomUUID())
    }
}
