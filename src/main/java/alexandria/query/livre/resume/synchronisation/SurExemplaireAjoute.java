package alexandria.query.livre.resume.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.query.livre.resume.modele.ResumeLivre;
import arpinum.ddd.event.EventCaptor;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Optional;

public class SurExemplaireAjoute implements EventCaptor<ExemplaireAjouteEvenement> {

    @Inject
    public SurExemplaireAjoute(Jongo jongo, CatalogueLivre catalogue) {
        this.jongo = jongo;
        this.catalogue = catalogue;
    }

    @Override
    public void execute(ExemplaireAjouteEvenement evenement) {
        final MongoCollection collection = this.jongo.getCollection("vue_resumelivre");
        boolean resumePresent = collection.count("{_id:#}", evenement.getIsbn()) > 0;
        if (resumePresent) {
            incrementeLeNombreDExemplaire(evenement, collection);
        } else {
            enregistreUnNouvelExemplaire(evenement, collection);
        }
    }

    private void incrementeLeNombreDExemplaire(ExemplaireAjouteEvenement evenement, MongoCollection collection) {
        collection.update("{_id:#}", evenement.getIsbn()).with("{ $inc: { nombre : 1}}");
    }

    private void enregistreUnNouvelExemplaire(ExemplaireAjouteEvenement evenement, MongoCollection collection) {
        final Optional<DetailsLivre> details = catalogue.parIsbn(evenement.getIsbn());

        collection.save(new ResumeLivre(evenement.getIsbn(), details.orElse(DetailsLivre.LIVRE_VIDE).titre));
    }

    private final Jongo jongo;

    private final CatalogueLivre catalogue;
}
