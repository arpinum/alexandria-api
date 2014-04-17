package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.emprunt.EntrepotEmprunt;

@SuppressWarnings("UnusedDeclaration")
public class LocalisateurEntrepotsMemoire extends LocalisateurEntrepots {

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return entrepotBibliothequesMemoire;
    }

    @Override
    protected EntrepotEmprunt getEmprunt() {
        return entrepotEmpruntsMemoire;
    }

    private final EntrepotBibliothequesMemoire entrepotBibliothequesMemoire = new EntrepotBibliothequesMemoire();

    private final EntrepotEmpruntsMemoire entrepotEmpruntsMemoire = new EntrepotEmpruntsMemoire();
}
