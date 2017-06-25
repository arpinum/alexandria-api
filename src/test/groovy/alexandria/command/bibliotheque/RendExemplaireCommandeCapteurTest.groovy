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

    def "demande à rendre l'exemplaire"() {
        given:
        def bibliotheque = new Bibliotheque('id', new Lecteur())
        LocalisateurEntrepots.bibliotheques().add(bibliotheque)
        def exemplaire = bibliotheque.ajouteExemplaire('isbn')._2
        LocalisateurEntrepots.exemplaires().add(exemplaire)
        bibliotheque.sort(exemplaire, new Lecteur(''))

        when:
        def évènements = capteur.doExecute(new RendExemplaireCommande('id', exemplaire.id))

        then:
        with(évènements.first()) {
            targetId == bibliotheque.id
            idExemplaire == exemplaire.id
        }
    }
}
