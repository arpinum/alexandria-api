package alexandria.query.livre.details.recherche

import arpinum.query.WithJongo
import org.junit.Rule
import spock.lang.Specification

class RechercheDetailsLivreCapteurTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    def "peut retourner un livre"() {
        given:
        jongo.collection("vue_detailslivre") << [_id: "isbn", titre: "titre", image: "image", exemplaires: [[emailLecteur: "body"]]]
        def capteur = new RechercheDetailsLivreCapteur()

        when:
        def livre = capteur.execute(new RechercheDetailsLivre("isbn"), jongo.jongo())

        then:
        livre != null
        livre.titre == "titre"
        livre.image == "image"
        livre.exemplaires.size() == 1
        livre.exemplaires[0].emailLecteur == "body"
    }
}
