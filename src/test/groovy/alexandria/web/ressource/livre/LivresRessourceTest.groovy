package alexandria.web.ressource.livre
import alexandria.recherche.livre.ResumeLivre
import fr.arpinum.graine.infrastructure.bus.ResultatExecution
import fr.arpinum.graine.recherche.BusRecherche
import spock.lang.Specification

class LivresRessourceTest extends Specification {

    BusRecherche bus = Mock(BusRecherche)
    LivresRessource ressource

    void setup() {
        ressource = new LivresRessource(bus)
    }

    def "retourne bien tous les livres"() {
        given:
        def livres = [new ResumeLivre()]
        bus.posteToutDeSuite(_) >> ResultatExecution.succes(livres)

        when:
        def reponse = ressource.recherche()

        then:
        reponse == livres
    }
}
