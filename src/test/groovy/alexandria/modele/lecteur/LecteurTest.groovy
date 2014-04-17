package alexandria.modele.lecteur

import alexandria.modele.bibliotheque.Exemplaire
import alexandria.modele.emprunt.Emprunt
import spock.lang.Specification

class LecteurTest extends Specification {

    def "peut emprunter un exemplaire dans une bibliotheque"() {
        given:
        def lecteur = new Lecteur("email")
        def uuid = UUID.randomUUID()
        def exemplaire = new Exemplaire("mon isbn", uuid)

        when:
        Emprunt emprunt = lecteur.emprunte(exemplaire)

        then:
        emprunt != null
        emprunt.getIdentifiantLecteur() == lecteur.getId()
        emprunt.getExemplaire() == exemplaire
        emprunt.getId() != null
    }


}
