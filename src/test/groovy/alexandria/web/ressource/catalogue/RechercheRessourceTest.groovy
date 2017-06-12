package alexandria.web.ressource.catalogue

import catalogue.CatalogueLivre
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.collection.Vector
import io.vavr.concurrent.Future
import spock.lang.Specification

import javax.ws.rs.container.AsyncResponse

class RechercheRessourceTest extends Specification {

    def "peut demander au catalogue de rechercher"() {
        given:
        def livres = Vector.empty()
        def ressource = new RechercheRessource(unCatalogueQuiRetourne(livres))
        def response = Mock(AsyncResponse)
        when:
        ressource.recherche(response,"lachaine")

        then:
        1 * response.resume(livres)
    }

    private CatalogueLivre unCatalogueQuiRetourne(Vector livres) {
        def catalogue = Mock(CatalogueLivre)
        catalogue.recherche("lachaine") >> Future.successful(MoreExecutors.newDirectExecutorService(), livres)
        catalogue
    }
}
