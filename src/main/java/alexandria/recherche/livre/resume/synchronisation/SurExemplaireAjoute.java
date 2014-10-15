package alexandria.recherche.livre.resume.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.recherche.livre.resume.modele.ResumeLivre;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Optional;

public class SurExemplaireAjoute implements CapteurEvenement<ExemplaireAjouteEvenement> {

    @Inject
    public SurExemplaireAjoute(Jongo jongo, CatalogueLivre catalogue) {
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

        collection.save(new ResumeLivre(evenement.isbn, details.orElse(DetailsLivre.LIVRE_VIDE).titre));
    }

    private final Jongo jongo;
    private final CatalogueLivre catalogue;
}
