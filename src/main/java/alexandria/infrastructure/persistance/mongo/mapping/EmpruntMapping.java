package alexandria.infrastructure.persistance.mongo.mapping;

import alexandria.modele.emprunt.Emprunt;
import org.mongolink.domain.mapper.AggregateMap;

public class EmpruntMapping extends AggregateMap<Emprunt>{

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getEmailLecteur());
        property().onProperty(element().getExemplaire());
        property().onField("dateRemise");
    }
}
