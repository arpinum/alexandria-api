package alexandria.command.bibliotheque

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.lecteur.Lecteur
import org.junit.Rule
import spock.lang.Specification

class RendExemplaireCommandeCapteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()
    
    def capteur = new RendExemplaireCommandeCapteur()

    def "rend l'exemplaire et retourne l'évènement"() {
        given:
        def bibliotheque = new Bibliotheque("id", new Lecteur(""))
        def exemplaire = bibliotheque.ajouteExemplaire("isbn")._2
        LocalisateurEntrepots.bibliotheques().add(bibliotheque)
        LocalisateurEntrepots.exemplaires().add(exemplaire)
        bibliotheque.sort(exemplaire, new Lecteur("anriutesrn"))

        when:
        def résultat = capteur.doExecute(new RendExemplaireCommande(bibliotheque.id, exemplaire.id))

        then:
        with(résultat.first()) {
            targetId == bibliotheque.id
            idExemplaire == exemplaire.id
        }
    }
}
