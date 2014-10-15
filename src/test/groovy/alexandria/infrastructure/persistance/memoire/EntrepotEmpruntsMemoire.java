package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.emprunt.Emprunt;
import alexandria.modele.emprunt.EntrepotEmprunts;
import fr.arpinum.graine.infrastructure.persistance.memoire.EntrepotMemoireAvecUuid;

import java.util.Optional;

public class EntrepotEmpruntsMemoire extends EntrepotMemoireAvecUuid<Emprunt> implements EntrepotEmprunts {
    @Override
    public boolean existePour(Exemplaire exemplaire) {
        return entites.stream().anyMatch(e -> e.getExemplaire().equals(exemplaire));
    }

    @Override
    public Optional<Emprunt> empruntCourantDe(Exemplaire exemplaire) {
        return entites.stream().filter(e-> e.getExemplaire().equals(exemplaire) && !e.rendu()).findFirst();
    }
}
