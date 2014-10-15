package alexandria.recherche.livre.details.synchronisation
import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Exemplaire
import alexandria.modele.emprunt.Emprunt
import alexandria.modele.emprunt.EmpruntRenduEvenement
import alexandria.modele.lecteur.Lecteur
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class SurEmpruntRenduTest extends Specification {

    @Rule
    AvecJongo jongo = new AvecJongo()

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def "peut mettre à jour la disponibilité"() {
        given:
        def bibli = UUID.randomUUID()
        jongo.collection("vue_detailslivre") << [_id:"isbn", exemplaires:[[emailLecteur:"email@email", idBibliotheque:bibli, disponible:false]]]
        def capteur = new SurEmpruntRendu(jongo.jongo())
        def emprunt = new Emprunt(new Lecteur("email@email"), new Exemplaire("isbn", bibli))
        LocalisateurEntrepots.emprunts().ajoute(emprunt)

        when:
        capteur.executeEvenement(new EmpruntRenduEvenement(emprunt.id))

        then:
        def enregistrement = jongo.collection("vue_detailslivre").findOne()
        enregistrement.exemplaires[0].disponible == true
    }
}
