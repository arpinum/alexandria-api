package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;

@SuppressWarnings("UnusedDeclaration")
public class LocalisateurEntrepotsMemoire extends LocalisateurEntrepots {

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return entrepotBibliothequesMemoire;
    }

    private final EntrepotBibliothequesMemoire entrepotBibliothequesMemoire = new EntrepotBibliothequesMemoire();

}
