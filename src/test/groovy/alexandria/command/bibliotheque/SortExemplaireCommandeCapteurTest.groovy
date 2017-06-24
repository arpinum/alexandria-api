package alexandria.command.bibliotheque

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.lecteur.Lecteur
import org.junit.Rule
import spock.lang.Specification


class SortExemplaireCommandeCapteurTest extends Specification {

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def capteur = new SortExemplaireCommandeCapteur()

    def "sort l'exemplaire"() {
        given:
        def bibliotheque = new Bibliotheque('id', new Lecteur('idLecteur'))
        def exemplaire = bibliotheque.ajouteExemplaire('isbn')._2
        LocalisateurEntrepots.bibliotheques().add(bibliotheque)
        LocalisateurEntrepots.exemplaires().add(exemplaire);

        when:
        def résultat = capteur.execute(new SortExemplaireCommande(bibliotheque.id, new Lecteur('emprunteur'), exemplaire.id))

        then:
        def emprunt = résultat._1
        with(emprunt) {
            idBibliothèque == 'id'
            idExemplaire == exemplaire.id
            idLecteur == 'emprunteur'
        }
        def évènement = résultat._2
        with(évènement.get(0)) {
            targetId == 'id'
            idExemplaire == exemplaire.id
            idLecteur == 'emprunteur'
        }
    }
}
