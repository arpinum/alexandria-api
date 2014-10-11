package alexandria.recherche.livre.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;

public class SynchronisationDetailsLivre implements CapteurEvenement<ExemplaireAjouteEvenement>{
    @Override
    public void executeEvenement(ExemplaireAjouteEvenement evenement) {

    }
}
