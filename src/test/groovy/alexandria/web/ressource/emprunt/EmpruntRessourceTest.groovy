package alexandria.web.ressource.emprunt

import alexandria.commande.emprunt.RendEmpruntCommande
import fr.arpinum.graine.commande.BusCommande
import fr.arpinum.graine.web.restlet.InitialisateurRessource
import spock.lang.Specification

class EmpruntRessourceTest extends Specification {

    def bus = Mock(BusCommande)

    def "peut demander à rendre un emprunt"() {
        given:
        def ressource = new EmpruntRessource(bus)
        def id = UUID.randomUUID()
        InitialisateurRessource.pour(ressource).avecParamètre("isbn", "isbn").avecParamètre("idBibliotheque", id.toString()).initialise()

        when:
        ressource.rend()

        then:
        1 * bus.envoie({
            it.isbn == "isbn"
            it.idBibliotheque == id
        } as RendEmpruntCommande)
    }
}
