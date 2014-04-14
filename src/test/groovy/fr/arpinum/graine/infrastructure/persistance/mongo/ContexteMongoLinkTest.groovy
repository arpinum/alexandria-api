package fr.arpinum.graine.infrastructure.persistance.mongo

import org.mongolink.MongoSession
import org.mongolink.MongoSessionManager
import spock.lang.Specification

class ContexteMongoLinkTest extends Specification {

    def sessionManager = Mock(MongoSessionManager)
    def session = Mock(MongoSession)

    def "démarre une session au début d'une commande"() {
        given:
        def contexte = new ContexteMongoLink(sessionManager)
        sessionManager.createSession() >> session

        when:
        contexte.avantExecution(null)

        then:
        1 * session.start()
        contexte.sessionCourante() == session
    }

    def "stop la session finalement"() {
        given:
        def contexte = new ContexteMongoLink(sessionManager)
        sessionManager.createSession() >> session

        when:
        contexte.finalement()

        then:
        1 * session.stop()
    }

    def "clear la session si erreur"() {
        given:
        def contexte = new ContexteMongoLink(sessionManager)
        sessionManager.createSession() >> session

        when:
        contexte.surErreur()

        then:
        1 * session.clear()
    }


}
