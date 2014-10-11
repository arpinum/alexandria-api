package alexandria.recherche.livre.synchronisation

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.LocalisateurEntrepots
import alexandria.modele.bibliotheque.Bibliotheque
import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement
import alexandria.modele.lecteur.Lecteur
import catalogue.CatalogueLivre
import catalogue.DetailsLivre
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class SynchronisationDetailsLivreTest extends Specification {

    @Rule
    AvecJongo jongo = new AvecJongo()

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def catalogue = Mock(CatalogueLivre)

    SynchronisationDetailsLivre capteur

    void setup() {
        capteur = new SynchronisationDetailsLivre(jongo.jongo(), catalogue)
    }

    def "peut créer le résumé du livre"() {
        given:
        def bibliotheque = new Bibliotheque(new Lecteur("email@email"))
        def evenement = new ExemplaireAjouteEvenement("isbn", bibliotheque.getId())
        catalogue.parIsbn("isbn") >> Optional.of(new DetailsLivre(titre: "titre", image: "image"))
        LocalisateurEntrepots.bibliotheques().ajoute(bibliotheque)

        when:
        capteur.execute(evenement)

        then:
        def record = jongo.collection("vue_detailslivre").findOne()
        record != null
        record._id == "isbn"
        record.titre == "titre"
        record.image == "image"
        record.exemplaires != null
        record.exemplaires[0].emailLecteur == "email@email"
    }
}
