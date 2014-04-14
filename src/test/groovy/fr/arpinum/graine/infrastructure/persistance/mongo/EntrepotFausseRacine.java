package fr.arpinum.graine.infrastructure.persistance.mongo;

import fr.arpinum.graine.modele.Entrepot;
import org.mongolink.MongoSession;

public class EntrepotFausseRacine extends EntrepotMongoLink<String, FausseRacine> implements Entrepot<String, FausseRacine> {

    protected EntrepotFausseRacine(MongoSession session) {
        super(session);
    }


}
