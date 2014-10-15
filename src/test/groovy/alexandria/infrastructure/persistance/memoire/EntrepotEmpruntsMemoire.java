package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EntrepotEmprunts;
import fr.arpinum.graine.infrastructure.persistance.memoire.EntrepotMemoireAvecUuid;

public class EntrepotEmpruntsMemoire extends EntrepotMemoireAvecUuid<Emprunt> implements EntrepotEmprunts {
    @Override
    public boolean existePour(Exemplaire exemplaire) {
        return entites.stream().anyMatch(e -> e.getExemplaire().equals(exemplaire));
    }
}
