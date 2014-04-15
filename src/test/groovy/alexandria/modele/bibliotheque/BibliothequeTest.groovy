package alexandria.modele.bibliotheque
import alexandria.modele.lecteur.Lecteur
import fr.arpinum.graine.modele.evenement.AvecBusEvenement
import org.junit.Rule
import spock.lang.Specification

class BibliothequeTest extends Specification {

    @Rule
    AvecBusEvenement busEvenement = new AvecBusEvenement()

    def "peut créer une bibliothèque pour un lecteur"() {
        given:
        def lecteur = new Lecteur("bob@sponge.com")
        
        when:
        def bibliotheque = new Bibliotheque(lecteur)

        then:
        bibliotheque.emailLecteur() == "bob@sponge.com"
        bibliotheque.getId() != null
    }

    def "peut contenir un livre"() {
        given:
        def uneBibliotheque = uneBibliotheque()

        when:
        def exemplaire = uneBibliotheque.ajouteExemplaire("mon isbn")

        then:
        exemplaire != null
        exemplaire.isbn() == "mon isbn"
        uneBibliotheque.contient(exemplaire)
    }

    def "émet un évènement en cas d'ajout d'un exemplaire"() {
        given:
        def uneBibliotheque = uneBibliotheque()

        when:
        def exemplaire = uneBibliotheque.ajouteExemplaire("mon isbn")

        then:
        def evenement = busEvenement.bus().dernierEvement(ExemplaireAjouteEvenement)
        evenement != null
        evenement.isbn == "mon isbn"
        evenement.idBibliotheque == uneBibliotheque.getId()
    }

    Bibliotheque uneBibliotheque() {
        return new Bibliotheque(new Lecteur(""));
    }
}
