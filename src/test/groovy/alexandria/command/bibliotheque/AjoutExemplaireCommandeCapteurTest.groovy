package alexandria.command.bibliotheque

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.lecteur.Lecteur
import org.junit.Rule
import spock.lang.Specification

class AjoutExemplaireCommandeCapteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def "ajoute l'exemplaire"() {
        given:
        def lecteur = new Lecteur('idLecteur')
        def bibliotheque = new Bibliotheque("id", lecteur)
        LocalisateurEntrepots.bibliotheques().add(bibliotheque)
        def capteur = new AjoutExemplaireCommandeCapteur()

        when:
        def résultat = capteur.execute(new AjoutExemplaireCommande('id', 'isbn', lecteur))

        then:
        résultat != null
        def idExemplaire = résultat._1
        idExemplaire != null
        def évènement = résultat._2.get(0)
        with(évènement) {
            targetId == idExemplaire
            isbn == 'isbn'
            idLecteur == 'idLecteur'
            idBibliothèque == 'id'
        }
    }
}
