package alexandria.command.bibliotheque

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.bibliotheque.BibliothequeCréée
import alexandria.modele.lecteur.Lecteur
import arpinum.ddd.event.Event
import io.vavr.Tuple2
import io.vavr.collection.Seq
import org.junit.Rule
import spock.lang.Specification

class CreeBibliothequeParDéfautCommandeCapteurTest extends Specification {

    def handler = new CreeBibliothequeParDéfautCommandeCapteur()

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def "crée la bibliothèque pour le lecteur"() {
        given:
        def commande = new CreeBibliothequeParDéfautCommande(new Lecteur("id"))

        when:
        def résultat = handler.execute(commande)

        then:
        résultat != null
        résultat._2.get(0) instanceof BibliothequeCréée
        résultat._1 == "id"
    }

    def "ne crée pas la bibliothèque si elle existe"() {
        given:
        def lecteur = new Lecteur("id")
        def bibliotheque = new Bibliotheque("id", lecteur)
        LocalisateurEntrepots.bibliotheques().add(bibliotheque)

        when:
        def résultat = handler.execute(new CreeBibliothequeParDéfautCommande(lecteur))

        then:
        résultat._1 == "id"
        résultat._2.isEmpty()
    }
}
