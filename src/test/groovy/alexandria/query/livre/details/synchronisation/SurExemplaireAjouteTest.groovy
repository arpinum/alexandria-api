package alexandria.query.livre.details.synchronisation

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement
import arpinum.query.WithJongo
import catalogue.CatalogueLivre
import catalogue.DetailsLivre
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.concurrent.Future
import io.vavr.control.Option
import org.junit.Rule
import spock.lang.Specification

class SurExemplaireAjouteTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    def catalogue = Mock(CatalogueLivre)

    SurExemplaireAjoute capteur

    void setup() {
        capteur = new SurExemplaireAjoute(jongo.jongo(), catalogue)
    }

    def "peut créer le détail du livre"() {
        given:

        def evenement = new ExemplaireAjouteEvenement("getId", "lecteur", "isbn")
        catalogue.parIsbn("isbn") >> Future.successful(MoreExecutors.newDirectExecutorService(), Option.of(new DetailsLivre(titre: "titre", image: "image")))

        when:
        capteur.execute(evenement)

        then:
        def record = jongo.collection("vue_detailslivre").findOne()
        record != null
        record._id == "isbn"
        record.titre == "titre"
        record.image == "image"
        record.exemplaires != null
        record.exemplaires[0].emailLecteur == "lecteur"
        record.exemplaires[0].idBibliotheque == "getId"
        record.exemplaires[0].disponible
    }
}
