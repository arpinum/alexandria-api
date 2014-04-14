package alexandria.modele.bibliotheque

import alexandria.modele.lecteur.Lecteur
import spock.lang.Specification

class BibliothequeTest extends Specification {

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

    Bibliotheque uneBibliotheque() {
        return new Bibliotheque(new Lecteur(""));
    }
}
