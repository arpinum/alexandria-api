package alexandria.recherche.livre

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement
import catalogue.CatalogueLivre
import catalogue.DetailsLivre
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class HandlerExemplaireAjouteTest extends Specification {

    @Rule
    public AvecJongo jongo = new AvecJongo()

    def catalogue = Mock(CatalogueLivre)

    def "créer un livre s'il n'existe pas"(){
        given:"Attendu un evénement d'ajout d'exemplaire"
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement("mon isbn", UUID.randomUUID())

        when:"Nous invoquons la méthode execute du souscripteur d'événement"
        capteur().execute(exemplaireAjouteEvenement)

        then:"Nous vérifions que le livre a été ajouté"
        def resume = jongo.collection("vue_resumelivre").findOne()
        resume != null
        resume._id == "mon isbn"
        resume.nombre == 1
    }



    def "ajoute un exemplaire à un livre existant"(){
        given:
        jongo.collection("vue_resumelivre") << [_id: "mon isbn", titre: "Harry Potter", nombre: 2]
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement("mon isbn", UUID.randomUUID())

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
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement("mon isbn", UUID.randomUUID())
        catalogue.parIsbn(_) >> Optional.of(new DetailsLivre(titre :"Harry Potter"))

        when:
        capteur().execute(exemplaireAjouteEvenement)

        then:"Nous vérifions que le livre a été ajouté"
        def resume = jongo.collection("vue_resumelivre").findOne()
        resume.titre == "Harry Potter"
    }

    private capteur() {
        catalogue.parIsbn(_) >> Optional.of(new DetailsLivre())
        new CapteurExemplaireAjoute(jongo.jongo(), catalogue)
    }
}
