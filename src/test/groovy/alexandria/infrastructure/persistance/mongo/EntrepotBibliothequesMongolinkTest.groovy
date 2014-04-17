package alexandria.infrastructure.persistance.mongo

import alexandria.modele.bibliotheque.Exemplaire
import fr.arpinum.graine.infrastructure.persistance.mongo.AvecMongolink
import org.junit.Rule
import spock.lang.Specification

class EntrepotBibliothequesMongolinkTest extends Specification {

    @Rule
    AvecMongolink mongolink = AvecMongolink.avecPackage("alexandria.infrastructure.persistance.mongo.mapping")

    def "peut récupérer une bibliotheque"() {
        given:
        def identifiant = UUID.randomUUID()
        mongolink.collection("bibliotheque") << [_id: identifiant, emailLecteur : "email", exemplaires: [[isbn:"isbn", identifiantBibliotheque:identifiant]]]

        when:
        def bibliotheque = new EntrepotBibliothequesMongolink(mongolink.sessionCourante()).get(identifiant)

        then:
        bibliotheque != null
        bibliotheque.getId() == identifiant
        bibliotheque.emailLecteur() == "email"
        bibliotheque.contient(new Exemplaire("isbn", identifiant))
    }

    def "peut retrouver par email"() {
        given:
        mongolink.collection("bibliotheque") << [[_id:UUID.randomUUID(), emailLecteur: "autreemail"], [_id:UUID.randomUUID(), emailLecteur: "monemail"]]

        when:
        def optionnelle = new EntrepotBibliothequesMongolink(mongolink.sessionCourante()).parEmailLecteur("monemail")

        then:
        optionnelle.isPresent()
        optionnelle.get().emailLecteur() == "monemail"
    }
}
