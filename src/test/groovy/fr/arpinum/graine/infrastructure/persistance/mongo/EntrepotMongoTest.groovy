package fr.arpinum.graine.infrastructure.persistance.mongo

import org.junit.Rule
import spock.lang.Specification

class EntrepotMongoTest extends Specification {

    @Rule
    public AvecMongolink mongolink = AvecMongolink.avecPackage("fr.arpinum.graine.infrastructure.persistance.mongo.mapping")

    def setup() {
        entrepot = new EntrepotFausseRacine(mongolink.sessionCourante())
    }

    def "peut ajouter"() {
        when:
        entrepot.ajoute(new FausseRacine("1"))
        mongolink.nettoieSession();

        then:
        def elementTrouvé = mongolink.collection("fausseracine").findOne(_id: "1")
        elementTrouvé != null
    }


    def "peut supprimer"() {
        given:
        def racine = new FausseRacine("1")
        entrepot.ajoute(racine)

        when:
        entrepot.supprime(racine)
        mongolink.nettoieSession()

        then:
        entrepot.get("1") == null
    }


    EntrepotFausseRacine entrepot

}
