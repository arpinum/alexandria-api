package alexandria.recherche.livre.details.synchronisation;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EmpruntAjouteEvenement;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;
import org.jongo.Jongo;

import javax.inject.Inject;

public class SurEmpruntAjoute implements CapteurEvenement<EmpruntAjouteEvenement> {

    @Inject
    public SurEmpruntAjoute(Jongo jongo) {
        this.jongo = jongo;
    }

    @Override
    public void executeEvenement(EmpruntAjouteEvenement evenement) {
        final Emprunt emprunt = LocalisateurEntrepots.emprunts().get(evenement.emprunt);
        jongo.getCollection("vue_detailslivre").update("{_id:#, 'exemplaires.idBibliotheque':#}", emprunt.getExemplaire().isbn(), emprunt.getExemplaire().identifiantBibliotheque())
                .with("{$set:{'exemplaires.$.disponible':false}}");
    }

    private final Jongo jongo;
}
