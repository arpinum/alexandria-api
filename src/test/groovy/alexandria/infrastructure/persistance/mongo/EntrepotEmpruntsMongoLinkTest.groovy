package alexandria.infrastructure.persistance.mongo

import alexandria.modele.emprunt.Emprunt
import fr.arpinum.graine.infrastructure.persistance.mongo.AvecMongolink
import org.junit.Rule
import spock.lang.Specification

class EntrepotEmpruntsMongoLinkTest extends Specification {

    @Rule
    AvecMongolink mongolink = AvecMongolink.avecPackage("alexandria.infrastructure.persistance.mongo.mapping")

    def "peut recupérer un emprunt"() {
        given:
        UUID uuid = UUID.randomUUID()
        mongolink.collection("emprunt") << ["_id": uuid, "identifiantLecteur":"coucou@test.com", "exemplaire":[isbn:"isbn", "identifiantBibliotheque":uuid]]

        when:
        Emprunt emprunt =  new EntrepotEmpruntsMongoLink(mongolink.sessionCourante()).get(uuid)

        then:
        emprunt != null
        emprunt.id == uuid
        emprunt.identifiantLecteur == "coucou@test.com"
        emprunt.exemplaire.isbn() == "isbn"
        emprunt.exemplaire.identifiantBibliotheque() == uuid
    }
}
