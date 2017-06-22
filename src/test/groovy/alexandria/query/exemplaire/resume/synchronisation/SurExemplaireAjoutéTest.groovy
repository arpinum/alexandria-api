package alexandria.query.exemplaire.resume.synchronisation

import alexandria.modele.exemplaire.ExemplaireAjouté
import alexandria.modele.lecteur.FicheLecteur
import alexandria.modele.lecteur.RegistreLecteurs
import arpinum.query.WithJongo
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.concurrent.Future
import org.junit.Rule
import spock.lang.Specification

class SurExemplaireAjoutéTest extends Specification {

    @Rule
    WithJongo jongo = new WithJongo()

    def registre = Mock(RegistreLecteurs)

    SurExemplaireAjouté capteur

    void setup() {
        capteur = new SurExemplaireAjouté(jongo.jongo(), registre)
    }

    def "crée le résumé avec la ficher lecteur"() {
        given:
        def évènement = new ExemplaireAjouté(UUID.randomUUID(), "bibli", 'lecteur', 'isbn')
        def lecteur = new FicheLecteur("id", 'dusse', 'jb')
        registreRetournePour(lecteur, 'lecteur')

        when:
        capteur.execute(évènement)

        then:
        def record = jongo.collection('vue_resume_exemplaires').findOne()
        record._id == évènement.getTargetId()
        record.lecteur.id == lecteur.id
        record.lecteur.nom == lecteur.nom
        record.lecteur.prenom == lecteur.prenom
        record.idBibliotheque == 'bibli'
        record.isbn == 'isbn'
        record.disponible == true
    }

    private void registreRetournePour(FicheLecteur lecteur, String id) {
        registre.ficheDe(id) >> Future.successful(MoreExecutors.newDirectExecutorService(), lecteur)
    }
}
