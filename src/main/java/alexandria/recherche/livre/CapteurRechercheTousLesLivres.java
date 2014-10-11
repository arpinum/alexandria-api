package alexandria.recherche.livre;

import com.google.common.collect.Lists;
import fr.arpinum.graine.recherche.CapteurRechercheJongo;
import org.jongo.Jongo;

public class CapteurRechercheTousLesLivres extends CapteurRechercheJongo<TousLesLivres,Iterable<ResumeLivre>> {


    @Override
    protected Iterable<ResumeLivre> execute(TousLesLivres tousLesLivres, Jongo jongo) {
        return Lists.newArrayList(jongo.getCollection("vue_resumelivre").find().as(ResumeLivre.class));
    }
}
