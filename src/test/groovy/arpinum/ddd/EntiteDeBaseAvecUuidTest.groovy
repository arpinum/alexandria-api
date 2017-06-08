package arpinum.ddd

import spock.lang.Specification

class EntiteDeBaseAvecUuidTest extends Specification {

    def "génère un identifiant par défaut"() {
        given:
        def uneEntité = new UneEntite()

        expect:
        uneEntité.getId() != null
    }

    class UneEntite extends EntiteDeBaseAvecUuid {

    }
}
