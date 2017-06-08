package alexandria.query.livre.details.recherche;

import alexandria.query.livre.details.modele.Livre;
import arpinum.query.QueryHandlerJongo;
import org.jongo.Jongo;

public class RechercheDetailsLivreCapteur extends QueryHandlerJongo<RechercheDetailsLivre, Livre> {


    @Override
    protected Livre execute(RechercheDetailsLivre recherche, Jongo jongo) {
        return jongo.getCollection("vue_detailslivre").findOne("{_id:#}", recherche.isbn).as(Livre.class);
    }
}
