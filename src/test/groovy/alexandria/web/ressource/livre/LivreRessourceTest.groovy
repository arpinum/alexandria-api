package alexandria.web.ressource.livre

import alexandria.recherche.livre.details.modele.Livre
import alexandria.recherche.livre.details.recherche.RechercheDetailsLivre
import fr.arpinum.graine.infrastructure.bus.ResultatExecution
import fr.arpinum.graine.recherche.BusRecherche
import fr.arpinum.graine.web.restlet.InitialisateurRessource
import spock.lang.Specification

class LivreRessourceTest extends Specification {

    def bus = Mock(BusRecherche)

    LivreRessource ressource

    void setup() {
        ressource = new LivreRessource(bus)
    }

    def "peut retourner le livre"() {
        given:
        InitialisateurRessource.pour(ressource).avecParamètre("isbn", "isbn").initialise()
        def livre = new Livre()
        bus.envoieEtAttendReponse(_ as RechercheDetailsLivre) >> ResultatExecution.succes(livre)

        when:
        def reponse = ressource.represente()

        then:
        reponse == livre
    }
}
