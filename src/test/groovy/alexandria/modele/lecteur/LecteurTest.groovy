package alexandria.modele.lecteur
import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.bibliotheque.Bibliotheque
import fr.arpinum.graine.modele.evenement.AvecBusEvenement
import org.junit.Rule
import spock.lang.Specification

class LecteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    @Rule
    AvecBusEvenement avecBusEvenement = new AvecBusEvenement()

    def "peut créer avec email"() {
        given:
        def lecteur = new Lecteur("email@email")

        expect:
        lecteur.email == "email@email"
    }

    def "peut emprunter un livre"() {
        given:
        def lecteur = new Lecteur("email")
        def bibliotheque = new Bibliotheque(new Lecteur("test"))
        bibliotheque.ajouteExemplaire("isbn")

        when:
        def emprunt = lecteur.emprunte(bibliotheque, "isbn")

        then:
        emprunt != null
        emprunt.emailLecteur == "email"
        emprunt.exemplaire == bibliotheque.trouve("isbn").get()
    }

}
