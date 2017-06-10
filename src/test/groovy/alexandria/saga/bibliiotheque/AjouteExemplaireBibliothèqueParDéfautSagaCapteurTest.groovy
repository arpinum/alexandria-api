package alexandria.saga.bibliiotheque

import alexandria.command.bibliotheque.AjoutExemplaireCommande
import alexandria.command.bibliotheque.CreeBibliothequeParDéfautCommande
import alexandria.modele.bibliotheque.IdentifiantExemplaire
import alexandria.modele.lecteur.Lecteur
import alexandria.modele.lecteur.RegistreLecteurs
import arpinum.command.CommandBus
import io.vavr.concurrent.Future
import spock.lang.Specification


class AjouteExemplaireBibliothèqueParDéfautSagaCapteurTest extends Specification {

    def bus = Mock(CommandBus)

    def registre = Mock(RegistreLecteurs)

    def capteur = new AjouteExemplaireBibliothèqueParDéfautSagaCapteur(registre)

    def "récupère le lecteur, crée la bibliothèque, et ajoute l'exemplaire"() {
        given:
        def lecteur = new Lecteur('getId')
        registre.trouve('getId') >> Future.successful(lecteur)
        bus.send(_ as CreeBibliothequeParDéfautCommande) >> Future.successful('getId')
        def résultat = new IdentifiantExemplaire('getId', 'isbn')
        bus.send(_ as AjoutExemplaireCommande) >> Future.successful(résultat)

        when:
        def future = capteur.run(bus, new AjouteExemplaireBibliothèqueParDéfautSaga(idLecteur: 'getId', isbn: 'isbn'))

        then:
        future.get() == résultat
    }
}
