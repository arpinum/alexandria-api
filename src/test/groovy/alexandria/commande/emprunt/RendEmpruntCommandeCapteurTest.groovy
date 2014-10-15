package alexandria.commande.emprunt

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Exemplaire
import alexandria.modele.emprunt.Emprunt
import alexandria.modele.lecteur.Lecteur
import fr.arpinum.graine.modele.evenement.AvecBusEvenement
import org.junit.Rule
import spock.lang.Specification

class RendEmpruntCommandeCapteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    @Rule
    AvecBusEvenement busEvenement = new AvecBusEvenement()

    def "peut rendre un emprunt"() {
        given:
        def id = UUID.randomUUID()
        def emprunt = new Emprunt(new Lecteur("test"), new Exemplaire("isbn", id))
        LocalisateurEntrepots.emprunts().ajoute(emprunt)

        when:
        new RendEmpruntCommandeCapteur().execute(new RendEmpruntCommande(idBibliotheque: id, isbn: "isbn"))

        then:
        emprunt.rendu()
    }
}
