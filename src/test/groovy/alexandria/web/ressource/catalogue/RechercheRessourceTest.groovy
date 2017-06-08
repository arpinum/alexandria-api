package alexandria.web.ressource.catalogue

import catalogue.CatalogueLivre
import spock.lang.Specification

class RechercheRessourceTest extends Specification {

    def "peut demander au catalogue de rechercher"() {
        given:
        def catalogue = Mock(CatalogueLivre)
        def livres = []
        catalogue.recherche("lachaine") >> livres
        def ressource = new RechercheRessource(catalogue)

        when:
        def résultat = ressource.recherche("lachaine")

        then:
        résultat != null
        résultat.livres == livres
    }
}
