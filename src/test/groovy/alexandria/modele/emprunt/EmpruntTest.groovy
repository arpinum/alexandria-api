package alexandria.modele.emprunt
import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.lecteur.Lecteur
import fr.arpinum.graine.modele.evenement.AvecBusEvenement
import org.junit.Rule
import spock.lang.Specification

class EmpruntTest extends Specification {

    @Rule
    AvecEntrepotsMemoire avecEntrepotsMemoire = new AvecEntrepotsMemoire()

    @Rule
    AvecBusEvenement busEvenement = new AvecBusEvenement()


    def "ne peut pas emprunter le même exemplaire deux fois"() {
        given:
        def bibliotheque = new Bibliotheque(new Lecteur("test"))
        bibliotheque.ajouteExemplaire("isbn")
        def lecteur = new Lecteur("email")
        def emprunt = lecteur.emprunte(bibliotheque, "isbn")
        LocalisateurEntrepots.emprunts().ajoute(emprunt)

        when:
        lecteur.emprunte(bibliotheque, "isbn")

        then:
        thrown(ExemplaireDejaEmprunte)
    }

    def "rale si l'exemplaire n'est pas trouvé"() {
        given:
        def lecteur = new Lecteur("email")
        def bibliotheque = new Bibliotheque(new Lecteur("test"))

        when:
        lecteur.emprunte(bibliotheque, "isbn")

        then:
        thrown(ExemplaireInconnu)
    }

    def "propage un évènement"() {
        given:
        def lecteur = new Lecteur("email")
        def bibliotheque = new Bibliotheque(new Lecteur("test"))
        bibliotheque.ajouteExemplaire("isbn")

        when:
        def emprunt = lecteur.emprunte(bibliotheque, "isbn")

        then:
        def evenement = busEvenement.bus().dernierEvement(EmpruntAjouteEvenement)
        evenement != null
        evenement.emprunt == emprunt.id
    }

    def "un emprunt n'est pas rendu par défaut"() {
        given:
        def lecteur = new Lecteur("email")
        def bibliotheque = new Bibliotheque(new Lecteur("test"))
        bibliotheque.ajouteExemplaire("isbn")

        when:
        def emprunt = lecteur.emprunte(bibliotheque, "isbn")

        then:
        !emprunt.rendu()
    }

    def "peut rendre un emprunt"() {
        given:
        def lecteur = new Lecteur("email")
        def bibliotheque = new Bibliotheque(new Lecteur("test"))
        bibliotheque.ajouteExemplaire("isbn")
        def emprunt = lecteur.emprunte(bibliotheque, "isbn")

        when:
        emprunt.rend()

        then:
        emprunt.rendu()
    }

    def "propage un évènement quand l'emprunt est rendu"() {
        given:
        def lecteur = new Lecteur("email")
        def bibliotheque = new Bibliotheque(new Lecteur("test"))
        bibliotheque.ajouteExemplaire("isbn")
        def emprunt = lecteur.emprunte(bibliotheque, "isbn")

        when:
        emprunt.rend()

        then:
        def evenement = busEvenement.bus().dernierEvement(EmpruntRenduEvenement)
        evenement != null;
        evenement.emprunt == emprunt.id
    }
}
