package alexandria.commande.bibliotheque
import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Exemplaire
import fr.arpinum.graine.modele.evenement.AvecBusEvenement
import org.junit.Rule
import spock.lang.Specification

class AjoutExemplaireCommandeHandlerTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    @Rule
    AvecBusEvenement busEvenement = new AvecBusEvenement()

    def "peut créer la bibliothèque si elle n'existe pas"(){
        given:
        def commande = new AjoutExemplaireCommande(email: "body@email.com", isbn: "myisbn")

        when:
        def id = new AjoutLivreCommandeHandler().execute(commande)

        then:
        LocalisateurEntrepots.bibliotheques().get(id) != null
    }

    def "peut ajouter un exemplaire à la bibliothèque"(){
        given:
        def commande = new AjoutExemplaireCommande(email: "body@email.com", isbn: "myisbn")

        when:
        def id = new AjoutLivreCommandeHandler().execute(commande)

        then:
        def bibliotheque = LocalisateurEntrepots.bibliotheques().get(id)
        bibliotheque.contient(new Exemplaire("myisbn"))
    }
}
