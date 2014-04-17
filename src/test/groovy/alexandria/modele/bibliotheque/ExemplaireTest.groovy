package alexandria.modele.bibliotheque

import spock.lang.Specification

class ExemplaireTest extends Specification {

    static UUID uuid = UUID.randomUUID()
    static uuidAutre = UUID.randomUUID()

    def "deux exemplaires avec les mêmes propriétés sont égaux"() {
        given:
        def unExemplaire = new Exemplaire("toto", this.uuid)
        def unAutreExemplaire = new Exemplaire("toto", this.uuid)

        expect:
        unAutreExemplaire == unExemplaire
    }

    def "deux exemplaires avec le même isbn mais avec des id de biblio différents, sont différents"(){
        given:
        def unExemplaire = new Exemplaire("toto", uuid)
        def unAutreExemplaire = new Exemplaire("toto", uuidAutre)

        expect:
        unAutreExemplaire != unExemplaire
    }


    def "deux objets différents ont un hashcode différent"() {
        given:
        def unExemplaire = new Exemplaire("toto", uuid)
        def unAutreExemplaire = new Exemplaire("tutu", uuid)

        expect:
        unAutreExemplaire.hashCode() != unExemplaire.hashCode()
    }

    def "deux objets différents par leur identifiant biblio ont un hashcode différent"() {
        given:
        def unExemplaire = new Exemplaire("tutu", uuid)
        def unAutreExemplaire = new Exemplaire("tutu", uuidAutre)

        expect:
        unAutreExemplaire.hashCode() != unExemplaire.hashCode()
    }
}
