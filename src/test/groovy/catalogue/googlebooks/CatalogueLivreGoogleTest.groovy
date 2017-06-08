package catalogue.googlebooks

import spock.lang.Ignore
import spock.lang.Specification

class CatalogueLivreGoogleTest extends Specification {

    @Ignore
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
