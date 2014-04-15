package alexandria.recherche.livre;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import fr.arpinum.graine.modele.evenement.HandlerEvenement;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;

public class HandlerExemplaireAjoute implements HandlerEvenement<ExemplaireAjouteEvenement> {

    @Inject
    public HandlerExemplaireAjoute(Jongo jongo) {
        this.jongo = jongo;
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
        collection.save(new ResumeLivre(evenement.isbn, ""));
    }

    private final Jongo jongo;
}
