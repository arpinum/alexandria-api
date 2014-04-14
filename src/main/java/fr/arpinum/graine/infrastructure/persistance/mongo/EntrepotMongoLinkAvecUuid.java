package fr.arpinum.graine.infrastructure.persistance.mongo;

import fr.arpinum.graine.modele.EntrepotAvecUuid;
import fr.arpinum.graine.modele.RacineAvecUuid;
import org.mongolink.MongoSession;

import java.util.UUID;

@SuppressWarnings("UnusedDeclaration")
public abstract class EntrepotMongoLinkAvecUuid<TRacine extends RacineAvecUuid> extends EntrepotMongoLink<UUID, TRacine> implements EntrepotAvecUuid<TRacine> {

    protected EntrepotMongoLinkAvecUuid(MongoSession session) {
        super(session);
    }
}
