package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import arpinum.infrastructure.persistance.memoire.MemoryRepository;

public class EntrepotBibliothequesMemoire extends MemoryRepository<String, Bibliotheque> implements EntrepotBibliotheques {

}
