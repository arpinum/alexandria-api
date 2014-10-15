package alexandria.recherche.livre.details.recherche;

import alexandria.recherche.livre.details.modele.Livre;
import fr.arpinum.graine.recherche.CapteurRechercheJongo;
import org.jongo.Jongo;

public class RechercheDetailsLivreCapteur extends CapteurRechercheJongo<RechercheDetailsLivre, Livre> {


    @Override
    protected Livre execute(RechercheDetailsLivre recherche, Jongo jongo) {
        return jongo.getCollection("vue_detailslivre").findOne("{_id:#}", recherche.isbn).as(Livre.class);
    }
}
