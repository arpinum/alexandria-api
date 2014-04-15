package alexandria.recherche.livre

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class HandlerExemplaireAjouteTest extends Specification {

    @Rule
    public AvecJongo jongo = new AvecJongo()

    def "créer un livre s'il n'existe pas"(){
        given:"Attendu un evénement d'ajout d'exemplaire"
        def exemplaireAjouteEvenement = new ExemplaireAjouteEvenement("mon isbn", UUID.randomUUID())

        when:"Nous invoquons la méthode execute du souscripteur d'événement"
        new HandlerExemplaireAjoute(jongo.jongo()).execute(exemplaireAjouteEvenement)

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
        new HandlerExemplaireAjoute(jongo.jongo()).execute(exemplaireAjouteEvenement)

        then:
        def resume = jongo.collection("vue_resumelivre").findOne()
        resume != null
        resume._id == "mon isbn"
        resume.nombre == 3
    }
}
