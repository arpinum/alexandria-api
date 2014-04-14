package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import fr.arpinum.graine.infrastructure.persistance.memoire.EntrepotMemoireAvecUuid;

import java.util.Optional;

public class EntrepotBibliothequesMemoire extends EntrepotMemoireAvecUuid<Bibliotheque> implements EntrepotBibliotheques{
    @Override
    public Optional<Bibliotheque> parEmailLecteur(String email) {
        return entites.stream()
                .filter(bibli -> bibli.emailLecteur().equals(email))
                .findFirst();

    }
}
