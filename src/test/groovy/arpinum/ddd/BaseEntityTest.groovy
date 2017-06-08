package arpinum.ddd

import spock.lang.Specification

class BaseEntityTest extends Specification {

    def "deux entités avec le même id sont identiques"() {
        given:
        def premièreEntité = new UneEntite("1")
        def autreEntité = new UneEntite("1")

        expect:
        premièreEntité == autreEntité
    }

    def "deux entités avec deux ids sont différents"() {
        given:
        def premièreEntité = new UneEntite("1")
        def autreEntité = new UneEntite("2")

        expect:
        premièreEntité != autreEntité
    }


    class UneEntite extends BaseEntity<String> {

        UneEntite(String id) {
            super(id);
        }

    }
}
