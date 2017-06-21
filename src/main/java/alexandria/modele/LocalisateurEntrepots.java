package alexandria.modele;

import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.exemplaire.EntrepotExemplaires;

import javax.inject.Inject;

public abstract class LocalisateurEntrepots {

    public static void initialise(LocalisateurEntrepots instance) {
        LocalisateurEntrepots.instance = instance;
    }

    public static EntrepotBibliotheques bibliotheques() {
        return instance.getBibliotheques();
    }

    protected abstract EntrepotBibliotheques getBibliotheques();

    public static EntrepotExemplaires exemplaires() { return instance.getExemplaires();}

    protected abstract EntrepotExemplaires getExemplaires();

    @Inject
    private static LocalisateurEntrepots instance;
}
