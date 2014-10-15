package alexandria.recherche.livre.details.synchronisation;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EmpruntRenduEvenement;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;
import org.jongo.Jongo;

import javax.inject.Inject;

public class SurEmpruntRendu implements CapteurEvenement<EmpruntRenduEvenement> {

    @Inject
    public SurEmpruntRendu(Jongo jongo) {
        this.miseAJourDisponibilite = new MiseAJourDisponibilite(jongo);
    }

    @Override
    public void executeEvenement(EmpruntRenduEvenement evenement) {
        final Emprunt emprunt = LocalisateurEntrepots.emprunts().get(evenement.emprunt);
        final boolean disponible = true;
        miseAJourDisponibilite.metAJourDisponibilite(emprunt, disponible);
    }

    private final MiseAJourDisponibilite miseAJourDisponibilite;
}
