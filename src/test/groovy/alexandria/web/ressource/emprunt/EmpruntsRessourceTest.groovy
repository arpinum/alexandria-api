package alexandria.web.ressource.emprunt

import alexandria.commande.emprunt.EmpruntExemplaireCommande
import fr.arpinum.graine.commande.BusCommande
import fr.arpinum.graine.infrastructure.bus.ResultatExecution
import spock.lang.Specification

class EmpruntsRessourceTest extends Specification {

    def bus = Mock(BusCommande)

    def "peut demander à créer l'emprunt"() {
        given:
        def ressource = new EmpruntsRessource(bus)
        def commande = new EmpruntExemplaireCommande()
        def identifiantEmprunt = UUID.randomUUID()
        bus.envoieEtAttendReponse(commande) >> ResultatExecution.succes(identifiantEmprunt)

        when:
        def uuid = ressource.emprunte(commande)

        then:
        uuid == identifiantEmprunt
    }
}
