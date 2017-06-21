package alexandria.modele.bibliotheque

import alexandria.modele.lecteur.Lecteur
import spock.lang.Specification

class BibliothequeTest extends Specification {

    def "peut créer une bibliothèque pour un lecteur"() {
        given:
        def lecteur = new Lecteur("getId")

        when:
        def résultat = Bibliotheque.fabrique().pour(lecteur)

        then:
        résultat != null
        def bibliotheque = résultat._2
        bibliotheque.idLecteur() == "getId"
        bibliotheque.getId() != null
        def event = résultat._1
        event != null
        event.targetId == bibliotheque.id
        event.idLecteur == "getId"
    }

    def "rejoue création"() {
        given:
        def bibliotheque = new Bibliotheque()

        when:
        bibliotheque.rejoue(new BibliothequeCréée("getId", new Lecteur("lecteur")))

        then:
        bibliotheque.id == 'getId'
        bibliotheque.idLecteur() == 'lecteur'
    }

    Bibliotheque uneBibliotheque() {
        return new Bibliotheque("getId", new Lecteur(""));
    }
}
