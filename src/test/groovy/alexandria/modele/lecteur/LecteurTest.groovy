package alexandria.modele.lecteur

import spock.lang.Specification

class LecteurTest extends Specification {

    def "peut créer avec email"() {
        given:
        def lecteur = new Lecteur("email@email")

        expect:
        lecteur.email == "email@email"
    }
}
