package alexandria.modele.emprunt;

import alexandria.modele.bibliotheque.Exemplaire;
import fr.arpinum.graine.modele.EntrepotAvecUuid;

public interface EntrepotEmprunts extends EntrepotAvecUuid<Emprunt>{

    boolean existePour(Exemplaire exemplaire);
}
