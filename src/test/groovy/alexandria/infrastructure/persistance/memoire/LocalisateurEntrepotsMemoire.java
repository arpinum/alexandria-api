package alexandria.infrastructure.persistance.memoire;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.exemplaire.EntrepotExemplaires;

@SuppressWarnings("UnusedDeclaration")
public class LocalisateurEntrepotsMemoire extends LocalisateurEntrepots {

    @Override
    protected EntrepotBibliotheques getBibliotheques() {
        return entrepotBibliothequesMemoire;
    }

    @Override
    protected EntrepotExemplaires getExemplaires() {
        return emprunts;
    }

    private final EntrepotBibliothequesMemoire entrepotBibliothequesMemoire = new EntrepotBibliothequesMemoire();

    private final EntrepotExemplairessMemoire emprunts = new EntrepotExemplairessMemoire();
}
