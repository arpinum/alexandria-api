package catalogue.googlebooks

import spock.lang.Specification

class CatalogueLivreGoogleTest extends Specification {

    def "peut retourner une liste de livres"() {
        given:
        def catalogue = new CatalogueLivreGoogle()

        when:
        def résultat = catalogue.recherche("ddd")

        then:
        résultat != null
        résultat.first() != null
    }
}
