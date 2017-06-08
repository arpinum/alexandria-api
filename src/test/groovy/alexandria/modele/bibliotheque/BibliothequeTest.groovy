package alexandria.modele.bibliotheque

import alexandria.modele.lecteur.Lecteur
import spock.lang.Specification

class BibliothequeTest extends Specification {

    def "peut créer une bibliothèque pour un lecteur"() {
        given:
        def lecteur = new Lecteur("id")

        when:
        def résultat = Bibliotheque.fabrique().pour(lecteur)

        then:
        résultat != null
        def bibliotheque = résultat._2
        bibliotheque.idLecteur() == "id"
        bibliotheque.getId() != null
        def event = résultat._1
        event != null
        event.targetId == bibliotheque.id
        event.idLecteur == "id"
    }

    def "peut contenir un livre"() {
        given:
        def uneBibliotheque = uneBibliotheque()

        when:
        def résultat = uneBibliotheque.ajouteExemplaire(new Lecteur("id"), "mon isbn")

        then:
        def exemplaire = résultat._2()
        exemplaire != null
        exemplaire.isbn() == "mon isbn"
        exemplaire.identifiantBibliotheque() == uneBibliotheque.getId()
        uneBibliotheque.contient(exemplaire)
        def évènement = résultat._1
        évènement != null
        évènement.getTargetId() == uneBibliotheque.id
        évènement.isbn == "mon isbn"
    }

    def "peut rechercher un exemplaire"() {
        given:
        def bibliotheque = uneBibliotheque()
        bibliotheque.ajouteExemplaire(new Lecteur(""),"isbn")
        bibliotheque.ajouteExemplaire(new Lecteur(""),"autre")

        when:
        def exemplaireEventuel = bibliotheque.trouve("isbn")

        then:
        exemplaireEventuel != null
        exemplaireEventuel.isDefined()
        exemplaireEventuel.get().isbn == "isbn"

    }

    Bibliotheque uneBibliotheque() {
        return new Bibliotheque("id", new Lecteur(""));
    }
}
