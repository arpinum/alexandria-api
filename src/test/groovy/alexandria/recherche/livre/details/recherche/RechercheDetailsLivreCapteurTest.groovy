package alexandria.recherche.livre.details.recherche

import alexandria.recherche.livre.details.recherche.RechercheDetailsLivre
import alexandria.recherche.livre.details.recherche.RechercheDetailsLivreCapteur
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class RechercheDetailsLivreCapteurTest extends Specification {

    @Rule
    AvecJongo jongo = new AvecJongo()

    def "peut retourner un livre"() {
        given:
        jongo.collection("vue_detailslivre") << [_id:"isbn", titre:"titre", image:"image", exemplaires:[[emailLecteur:"body"]]]
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