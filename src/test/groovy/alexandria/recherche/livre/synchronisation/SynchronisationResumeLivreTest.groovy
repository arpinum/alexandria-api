package alexandria.recherche.livre.synchronisation

import alexandria.infrastructure.persistance.memoire.AvecEntrepotsMemoire
import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement
import catalogue.CatalogueLivre
import catalogue.DetailsLivre
import fr.arpinum.graine.recherche.AvecJongo
import org.junit.Rule
import spock.lang.Specification

class SynchronisationResumeLivreTest extends Specification {

    @Rule
    AvecJongo jongo = new AvecJongo()

    @Rule
    AvecEntrepotsMemoire entrepotsMemoire = new AvecEntrepotsMemoire()

    def catalogue = Mock(CatalogueLivre)

    SynchronisationResumeLivre capteur

    void setup() {
        capteur = new SynchronisationResumeLivre(jongo.jongo(), catalogue)

    }

    def "peut créer le résumé du livre"() {
        given:
        def evenement = new ExemplaireAjouteEvenement("isbn", UUID.randomUUID())
        catalogue.parIsbn("isbn") >> Optional.of(new DetailsLivre(titre: "titre"))

        when:
        capteur.execute(evenement)

        then:
        def record = jongo.collection("vue_resumelivre").findOne()
        record != null
        record._id == "isbn"
        record.titre == "titre"
        record.nombre == 1
    }

    def "peut incrémenter le nombre"() {
        given:
        def evenement = new ExemplaireAjouteEvenement("isbn", UUID.randomUUID())
        catalogue.parIsbn("isbn") >> Optional.of(new DetailsLivre(titre: "titre"))
        jongo.collection("vue_resumelivre") << [_id:"isbn", titre:"titre", nombre: 1]

        when:
        capteur.execute(evenement)

        then:
        def record = jongo.collection("vue_resumelivre").findOne()
        record != null
        record.nombre == 2
    }
}
