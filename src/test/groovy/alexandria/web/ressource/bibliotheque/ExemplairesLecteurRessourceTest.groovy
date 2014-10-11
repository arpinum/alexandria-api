package alexandria.web.ressource.bibliotheque

import alexandria.commande.bibliotheque.AjoutExemplaireCommande
import fr.arpinum.graine.commande.BusCommande
import fr.arpinum.graine.web.restlet.InitialisateurRessource
import spock.lang.Specification

class ExemplairesLecteurRessourceTest extends Specification {

    BusCommande bus = Mock(BusCommande)

    def "peut demander à créer un exemplaire"() {
        given:
        AjoutExemplaireCommande commande
        def ressource = new ExemplairesLecteurRessource(bus)
        InitialisateurRessource.pour(ressource)
                .avecParamètre("email", "monmail")
                .avecParamètre("isbn", "iiiiisbn")
                .initialise()
        when:
        ressource.ajoute()

        then:
        1 * bus.envoieEtAttendReponse({
            it.email == "monmail" && it.isbn == "iiiiisbn"
        })
    }
}
