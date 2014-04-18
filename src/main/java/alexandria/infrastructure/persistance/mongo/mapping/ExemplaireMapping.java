package alexandria.infrastructure.persistance.mongo.mapping;

import alexandria.modele.bibliotheque.Exemplaire;
import org.mongolink.domain.mapper.ComponentMap;

public class ExemplaireMapping extends ComponentMap<Exemplaire> {

    @Override
    public void map() {
        property().onField("isbn");
        property().onField("identifiantBibliotheque");
    }
}
