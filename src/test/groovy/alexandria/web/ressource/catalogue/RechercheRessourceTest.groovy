package alexandria.web.ressource.catalogue

import catalogue.CatalogueLivre
import fr.arpinum.graine.web.restlet.InitialisateurRessource
import spock.lang.Specification

class RechercheRessourceTest extends Specification {

    def "peut demander au catalogue de rechercher"() {
        given:
        def catalogue = Mock(CatalogueLivre)
        def livres = []
        catalogue.recherche("lachaine") >> livres
        def ressource = new RechercheRessource(catalogue)
        InitialisateurRessource.pour(ressource).avecQuery("q", "lachaine").initialise()

        when:
        def résultat = ressource.recherche()

        then:
        résultat == livres
    }
}
