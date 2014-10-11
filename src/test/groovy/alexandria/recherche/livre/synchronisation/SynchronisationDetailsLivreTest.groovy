package alexandria.recherche.livre.synchronisation

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class SynchronisationDetailsLivreTest extends Specification {

    @Rule
    AvecJongo jongo

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def "peut créer le résumé du livre"() {


    }
}
