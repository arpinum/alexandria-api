package alexandria.commande.emprunt

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.lecteur.Lecteur
import fr.arpinum.graine.modele.evenement.AvecBusEvenement
import org.junit.Rule
import spock.lang.Specification

class EmpruntExemplaireCommandeCapteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    @Rule
    AvecBusEvenement busEvenement = new AvecBusEvenement()

    def "peut créer un emprunt"() {
        given:
        def bibliotheque = new Bibliotheque(new Lecteur("test@test"))
        LocalisateurEntrepots.bibliotheques().ajoute(bibliotheque)
        bibliotheque.ajouteExemplaire("isbn")
        def commande = new EmpruntExemplaireCommande(identifiantBibliotheque: bibliotheque.id, emailLecteur: "email", isbn: "isbn")

        when:
        def uuid = new EmpruntExemplaireCommandeCapteur().execute(commande)

        then:
        def emprunt = LocalisateurEntrepots.emprunts().get(uuid)
        emprunt != null
    }
}
