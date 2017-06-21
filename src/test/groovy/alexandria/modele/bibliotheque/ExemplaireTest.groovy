package alexandria.modele.bibliotheque

import alexandria.modele.exemplaire.Exemplaire
import alexandria.modele.exemplaire.ExemplaireAjouté
import alexandria.modele.lecteur.Lecteur
import spock.lang.Specification

class ExemplaireTest extends Specification {

    def "est créé depuis une bibliothèque"() {
        given:
        def uneBibliotheque = uneBibliotheque()

        when:
        def résultat = uneBibliotheque.ajouteExemplaire("mon isbn")

        then:
        def exemplaire = résultat._2()
        exemplaire != null
        exemplaire.isbn() == "mon isbn"
        exemplaire.idBibliothèque() == uneBibliotheque.getId()
        exemplaire.id != null

        def évènement = résultat._1
        évènement != null
        évènement.targetId == exemplaire.id
        évènement.isbn == "mon isbn"
        évènement.idLecteur == "lecteur"
        évènement.idBibliothèque == uneBibliotheque.id
    }

    def "rejoue création"() {
        given:
        def évènement = new ExemplaireAjouté(UUID.randomUUID(), "bibli", "idLecteur", "isbn")
        def exemplaire = new Exemplaire()

        when:
        exemplaire.rejoue(évènement)

        then:
        exemplaire.id == évènement.getTargetId()
        exemplaire.isbn() == 'isbn'
        exemplaire.idBibliothèque() == "bibli"
    }

    Bibliotheque uneBibliotheque() {
        return new Bibliotheque("getId", new Lecteur("lecteur"));
    }
}
