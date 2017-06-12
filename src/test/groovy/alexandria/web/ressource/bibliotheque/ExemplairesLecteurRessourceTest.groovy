package alexandria.web.ressource.bibliotheque

import alexandria.saga.bibliiotheque.AjouteExemplaireBibliothèqueParDéfautSaga
import arpinum.command.CommandBus
import arpinum.web.SecurityContextBuilder
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
        ressource.ajoute(response, SecurityContextBuilder.forId("id"), new AjouteExemplaireBibliothèqueParDéfautSaga(isbn:"iiiiisbn"))

        then:
        1 * bus.send({
            it.isbn == "iiiiisbn" && it.idLecteur == 'id'
        } as AjouteExemplaireBibliothèqueParDéfautSaga) >> Future.successful(UUID.randomUUID())
    }
}
