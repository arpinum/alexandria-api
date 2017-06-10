package alexandria.modele.lecteur

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import org.junit.Rule
import spock.lang.Specification

class LecteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def "peut créer avec email"() {
        given:
        def lecteur = new Lecteur("getId@getId")

        expect:
        lecteur.id == "getId@getId"
    }

}
