package alexandria.modele.bibliotheque;

import fr.arpinum.graine.modele.EntrepotAvecUuid;

import java.util.Optional;

public interface EntrepotBibliotheques extends EntrepotAvecUuid<Bibliotheque> {

    Optional<Bibliotheque> parEmailLecteur(String email);

}
