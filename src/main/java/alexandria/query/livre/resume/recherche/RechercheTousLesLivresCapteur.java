package alexandria.query.livre.resume.recherche;

import alexandria.query.livre.resume.modele.ResumeLivre;
import arpinum.query.QueryHandlerJongo;
import com.google.common.collect.Lists;
import org.jongo.Jongo;

public class RechercheTousLesLivresCapteur extends QueryHandlerJongo<TousLesLivres, Iterable<ResumeLivre>> {


    @Override
    protected Iterable<ResumeLivre> execute(TousLesLivres tousLesLivres, Jongo jongo) {
        return Lists.newArrayList((Iterable<? extends ResumeLivre>) jongo.getCollection("vue_resumelivre").find().as(ResumeLivre.class));
    }
}
