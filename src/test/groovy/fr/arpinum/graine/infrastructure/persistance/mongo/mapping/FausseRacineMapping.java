package fr.arpinum.graine.infrastructure.persistance.mongo.mapping;

import fr.arpinum.graine.infrastructure.persistance.mongo.FausseRacine;
import org.mongolink.domain.mapper.AggregateMap;

@SuppressWarnings("UnusedDeclaration")
public class FausseRacineMapping extends AggregateMap<FausseRacine> {

    public FausseRacineMapping() {
        super(FausseRacine.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
    }
}
