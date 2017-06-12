package alexandria.query.livre.resume.synchronisation

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement
import alexandria.modele.lecteur.RegistreLecteurs
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

    def "créer un livre s'il n'existe pas"() {
        given:
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement(UUID.randomUUID().toString(), "idLecteur", "mon isbn")

        when:
        capteur().execute(exemplaireAjouteEvenement)

        then:
        def resume = jongo.collection("vue_resumelivre").findOne()
        resume != null
        resume._id == "mon isbn"
        resume.nombre == 1
    }


    def "ajoute un exemplaire à un livre existant"() {
        given:
        jongo.collection("vue_resumelivre") << [_id: "mon isbn", titre: "Harry Potter", nombre: 2]
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement(UUID.randomUUID().toString(), "idLecteur", "mon isbn")

        when:
        capteur().execute(exemplaireAjouteEvenement)

        then:
        def resume = jongo.collection("vue_resumelivre").findOne()
        resume != null
        resume._id == "mon isbn"
        resume.nombre == 3
    }

    def "peut récupérer le titre du livre"() {
        given:
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement(UUID.randomUUID().toString(), "idLecteur", "mon isbn")
        catalogueRetourne(new DetailsLivre(titre: "Harry Potter"))

        when:
        capteur().execute(exemplaireAjouteEvenement)

        then:
        def resume = jongo.collection("vue_resumelivre").findOne()
        resume.titre == "Harry Potter"
    }

    private capteur() {
        catalogueRetourne(new DetailsLivre())
        new SurExemplaireAjoute(jongo.jongo(), catalogue)
    }

    private void catalogueRetourne(DetailsLivre détails) {
        catalogue.parIsbn(_) >> Future.successful(MoreExecutors.newDirectExecutorService(), Option.of(détails))
    }
}
