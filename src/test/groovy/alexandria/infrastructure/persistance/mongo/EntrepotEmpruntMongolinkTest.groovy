package alexandria.infrastructure.persistance.mongo

import alexandria.modele.bibliotheque.Exemplaire
import alexandria.modele.emprunt.Emprunt
import alexandria.modele.lecteur.Lecteur
import fr.arpinum.graine.infrastructure.persistance.mongo.AvecMongolink
import org.junit.Rule
import spock.lang.Specification

class EntrepotEmpruntMongolinkTest extends Specification {

    @Rule
    AvecMongolink avecMongolink = AvecMongolink.avecPackage("alexandria.infrastructure.persistance.mongo.mapping")

    EntrepotEmpruntMongolink entrepot

    void setup() {
        entrepot = new EntrepotEmpruntMongolink(avecMongolink.sessionCourante())

    }

    def "peut persister un emprunt"() {
        given:
        def idBibliotheque = UUID.randomUUID()
        def emprunt = new Emprunt(new Lecteur("email"), new Exemplaire("isbn", idBibliotheque))

        when:
        entrepot.ajoute(emprunt)
        avecMongolink.nettoieSession()

        then:
        def enregistrement = avecMongolink.collection("emprunt").findOne([_id: emprunt.id])
        enregistrement != null
        enregistrement.emailLecteur == "email"
        enregistrement.exemplaire != null
        enregistrement.exemplaire.identifiantBibliotheque == idBibliotheque
    }

    def "peut dire qu'un emprunt existe"() {
        given:
        def exemplaire = new Exemplaire("isbn", UUID.randomUUID())
        def emprunt = new Emprunt(new Lecteur("email"), exemplaire)
        entrepot.ajoute(emprunt)
        avecMongolink.nettoieSession()

        when:
        def existe = entrepot.existePour(exemplaire)

        then:
        existe
    }
}
