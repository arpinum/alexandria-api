package alexandria.recherche.livre;

import fr.arpinum.graine.recherche.HandlerRechercheJongo;
import org.jongo.Jongo;

public class HandlerRechercheTousLesLivres extends HandlerRechercheJongo<TousLesLivres,Iterable<ResumeLivre>> {


    @Override
    protected Iterable<ResumeLivre> execute(TousLesLivres tousLesLivres, Jongo jongo) {
        return jongo.getCollection("vue_resumelivre").find().as(ResumeLivre.class);
    }
}
