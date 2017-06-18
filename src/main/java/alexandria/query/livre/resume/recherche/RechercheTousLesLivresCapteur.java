package alexandria.query.livre.resume.recherche;

import alexandria.query.livre.resume.modele.Livre;
import arpinum.query.QueryHandlerJongo;
import com.google.common.collect.Lists;
import org.jongo.Jongo;

public class RechercheTousLesLivresCapteur extends QueryHandlerJongo<TousLesLivres, Iterable<Livre>> {


    @Override
    protected Iterable<Livre> execute(TousLesLivres tousLesLivres, Jongo jongo) {
        return Lists.newArrayList((Iterable<? extends Livre>) jongo.getCollection("vue_resumelivre").find().as(Livre.class));
    }
}
