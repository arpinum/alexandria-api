package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.emprunt.EntrepotEmprunts;

@SuppressWarnings("UnusedDeclaration")
public class LocalisateurEntrepotsMemoire extends LocalisateurEntrepots {

    @Override
    protected EntrepotEmprunts getEmprunts() {
        return entrepotEmpruntsMemoire;
    }

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return entrepotBibliothequesMemoire;
    }

    private final EntrepotBibliothequesMemoire entrepotBibliothequesMemoire = new EntrepotBibliothequesMemoire();

    private final EntrepotEmpruntsMemoire entrepotEmpruntsMemoire = new EntrepotEmpruntsMemoire();
}
