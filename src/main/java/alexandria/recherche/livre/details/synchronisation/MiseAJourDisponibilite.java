package alexandria.recherche.livre.details.synchronisation;

import alexandria.modele.emprunt.Emprunt;
import org.jongo.Jongo;

public class MiseAJourDisponibilite {
    public MiseAJourDisponibilite(Jongo jongo) {
        this.jongo = jongo;
    }

    void metAJourDisponibilite(Emprunt emprunt, boolean disponible) {
        jongo.getCollection("vue_detailslivre").update("{_id:#, 'exemplaires.idBibliotheque':#}", emprunt.getExemplaire().isbn(), emprunt.getExemplaire().identifiantBibliotheque())
                .with("{$set:{'exemplaires.$.disponible':#}}", disponible);
    }

    Jongo jongo;
}