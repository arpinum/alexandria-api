package alexandria.modele.bibliotheque

import spock.lang.Specification

class ExemplaireTest extends Specification {

    def "deux exemplaires avec le même isbn sont égaux"() {
        given:
        def unExemplaire = new Exemplaire("toto")
        def unAutreExemplaire = new Exemplaire("toto")

        expect:
        unAutreExemplaire == unExemplaire
    }

    def "deux objets différents ont un hashcode différent"() {
        given:
        def unExemplaire = new Exemplaire("toto")
        def unAutreExemplaire = new Exemplaire("tutu")

        expect:
        unAutreExemplaire.hashCode() != unExemplaire.hashCode()
    }
}
