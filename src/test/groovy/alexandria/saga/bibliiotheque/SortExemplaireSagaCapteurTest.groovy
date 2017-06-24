package alexandria.saga.bibliiotheque

import alexandria.command.bibliotheque.SortExemplaireCommande
import alexandria.modele.bibliotheque.Emprunt
import alexandria.modele.lecteur.Lecteur
import alexandria.modele.lecteur.RegistreLecteurs
import arpinum.command.CommandBus
import com.google.common.util.concurrent.MoreExecutors
import io.vavr.concurrent.Future
import spock.lang.Specification

class SortExemplaireSagaCapteurTest extends Specification {

    def registre = Mock(RegistreLecteurs)

    def bus = Mock(CommandBus)

    def capteur = new SortExemplaireSagaCapteur(registre)

    def "cherche le lecteur et demande la sortie"() {
        given:
        def lecteur = new Lecteur('lecteur')
        registreRetourne(lecteur)
        def exemplaire = UUID.randomUUID()

        when:
        capteur.run(bus, new SortExemplaireSaga(idLecteur: 'lecteur', idBibliotheque: 'idBiblio', idExemplaire: exemplaire))

        then:
        1 * bus.send({ commande ->
            commande.idBibliothèque == 'idBiblio'
            commande.lecteur == lecteur
            commande.idExemplaire == exemplaire
        } as SortExemplaireCommande)
    }

    def "retourne l'emprunt"() {
        given:
        def lecteur = new Lecteur('lecteur')
        registreRetourne(lecteur)
        def emprunt = new Emprunt('', UUID.randomUUID(), '')
        busRetourne(emprunt)

        when:
        def result = capteur.run(bus, new SortExemplaireSaga(idLecteur: 'lecteur', idBibliotheque: 'idBiblio', idExemplaire: UUID.randomUUID()))

        then:
        result.get() == emprunt
    }

    private void registreRetourne(Lecteur lecteur) {
        registre.trouve(_) >> Future.successful(MoreExecutors.newDirectExecutorService(), lecteur)
    }

    private void busRetourne(Emprunt emprunt) {
        bus.send(_) >> Future.successful(MoreExecutors.newDirectExecutorService(), emprunt)
    }
}
