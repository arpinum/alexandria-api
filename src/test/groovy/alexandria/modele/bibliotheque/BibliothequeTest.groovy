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

    def "peut sortir un exemplaire"() {
        given:
        def (bibliotheque, exemplaire) = uneBibliothequeAvecEmplaire()
        def lecteur = new Lecteur('id')

        when:
        def résultat = bibliotheque.sort(exemplaire, lecteur)

        then:
        résultat != null
        def emprunt = résultat._2
        emprunt.idBibliothèque == bibliotheque.id
        emprunt.idLecteur == lecteur.id
        emprunt.idExemplaire == exemplaire.id
        def évènement = résultat._1
        évènement.targetId == bibliotheque.id
        évènement.idLecteur == lecteur.id
        évènement.idExemplaire == exemplaire.id
    }

    def "ne peut pas sortir le même exemplaire deux fois"() {
        given:
        def (bibliotheque, exemplaire) = uneBibliothequeAvecEmplaire()
        def lecteur = new Lecteur('id')
        bibliotheque.sort(exemplaire, lecteur)

        when:
        bibliotheque.sort(exemplaire, lecteur)

        then:
        thrown(ExemplaireDéjàSorti)
    }

    def "rejoue la sortie"() {
        given:
        def bibliotheque = uneBibliotheque()
        def idExemplaire = UUID.randomUUID()
        def évènement = new ExemplaireEmprunté(bibliotheque.id, idExemplaire, 'lecteur')

        when:
        bibliotheque.rejoue(évènement)

        then:
        def empruntEventuel = bibliotheque.emprunts.get(idExemplaire)
        empruntEventuel.isDefined()
        with(empruntEventuel.get()) {
            idBibliothèque == bibliotheque.id
            idLecteur == 'lecteur'
            idExemplaire == idExemplaire
        }
    }

    def "peut rendre un exemplaire"() {
        given:
        def (bibliothèque, exemplaire) = uneBibliothequeAvecEmplaire()
        bibliothèque.sort(exemplaire, new Lecteur(''))

        when:
        def évènement = bibliothèque.rend(exemplaire)

        then:
        bibliothèque.emprunts.isEmpty()
        with(évènement) {
            targetId == bibliothèque.id
            idExemplaire == exemplaire.id
        }
    }

    def "peut rejouer un retour"() {
        given:
        def (bibliothèque, exemplaire) = uneBibliothequeAvecEmplaire()
        bibliothèque.sort(exemplaire, new Lecteur(''))

        when:
        bibliothèque.rejoue(new ExemplaireRendu(bibliothèque.id, exemplaire.id))

        then:
        bibliothèque.emprunts.isEmpty()
    }

    Bibliotheque uneBibliotheque() {
        return new Bibliotheque("getId", new Lecteur(""));
    }

    def uneBibliothequeAvecEmplaire() {
        def bibliotheque = uneBibliotheque()
        def exemplaire = bibliotheque.ajouteExemplaire('isbn')._2
        return [bibliotheque, exemplaire]
    }
}
