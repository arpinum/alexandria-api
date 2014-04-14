package fr.arpinum.graine.infrastructure.persistance.mongo;

import fr.arpinum.graine.commande.SynchronisationCommande;
import fr.arpinum.graine.infrastructure.bus.Message;
import fr.arpinum.graine.modele.evenement.SynchronisationEvenement;
import org.mongolink.MongoSession;
import org.mongolink.MongoSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ContexteMongoLink implements SynchronisationCommande, SynchronisationEvenement {


    @Inject
    public ContexteMongoLink(MongoSessionManager sessionManager) {
        sessions = ThreadLocal.withInitial(sessionManager::createSession);
    }

    @Override
    public void avantExecution(Message<?> message) {
        LOGGER.debug("Démarrage d'une session");
        sessions.get().start();
    }

    @Override
    public void finalement() {
        LOGGER.debug("Arrêt de la session");
        sessions.get().stop();
        sessions.remove();
    }

    @Override
    public void surErreur() {
        LOGGER.debug("Nettoyage sur erreur de la session");
        sessions.get().clear();
    }

    public MongoSession sessionCourante() {
        return sessions.get();
    }

    private final ThreadLocal<MongoSession> sessions;
    private static final Logger LOGGER = LoggerFactory.getLogger(ContexteMongoLink.class);
}
