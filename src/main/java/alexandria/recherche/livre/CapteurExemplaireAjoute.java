package alexandria.recherche.livre;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Optional;

public class CapteurExemplaireAjoute implements CapteurEvenement<ExemplaireAjouteEvenement> {

    @Inject
    public CapteurExemplaireAjoute(Jongo jongo, CatalogueLivre catalogue) {
        this.jongo = jongo;
        this.catalogue = catalogue;
    }

    @Override
    public void executeEvenement(ExemplaireAjouteEvenement evenement) {
        final MongoCollection collection = this.jongo.getCollection("vue_resumelivre");
        boolean resumePresent = collection.count("{_id:#}", evenement.isbn) > 0;
        if (resumePresent) {
            incrementeLeNombreDExemplaire(evenement, collection);
        } else {
            enregistreUnNouvelExemplaire(evenement, collection);
        }
    }

    private void incrementeLeNombreDExemplaire(ExemplaireAjouteEvenement evenement, MongoCollection collection) {
        collection.update("{_id:#}", evenement.isbn).with("{ $inc: { nombre : 1}}");
    }

    private void enregistreUnNouvelExemplaire(ExemplaireAjouteEvenement evenement, MongoCollection collection) {
        final Optional<DetailsLivre> details = catalogue.parIsbn(evenement.isbn);

        collection.save(new ResumeLivre(evenement.isbn, details.orElse(LIVRE_VIDE).titre));
    }

    private final Jongo jongo;
    private final CatalogueLivre catalogue;
    private static final DetailsLivre LIVRE_VIDE = new DetailsLivre();
}
