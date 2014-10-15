package alexandria.modele.emprunt;

import alexandria.modele.bibliotheque.Exemplaire;
import fr.arpinum.graine.modele.EntrepotAvecUuid;

import java.util.Optional;

public interface EntrepotEmprunts extends EntrepotAvecUuid<Emprunt>{

    boolean existePour(Exemplaire exemplaire);

    Optional<Emprunt> empruntCourantDe(Exemplaire exemplaire);
}
