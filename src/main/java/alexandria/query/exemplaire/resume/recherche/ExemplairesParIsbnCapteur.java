package alexandria.query.exemplaire.resume.recherche;

import alexandria.query.exemplaire.resume.modele.RésuméExemplaire;
import arpinum.query.QueryHandlerJongo;
import io.vavr.collection.Vector;
import org.jongo.Jongo;

public class ExemplairesParIsbnCapteur extends QueryHandlerJongo<ExemplairesParIsbn, Iterable<RésuméExemplaire>> {

    @Override
    protected Iterable<RésuméExemplaire> execute(ExemplairesParIsbn exemplairesParIsbn, Jongo jongo) {
        return Vector.ofAll(jongo.getCollection("vue_resume_exemplaires")
                .find("{isbn:#}", exemplairesParIsbn.isbn).as(RésuméExemplaire.class));
    }
}
