package alexandria.modele;

import alexandria.modele.bibliotheque.EntrepotBibliotheques;

import javax.inject.Inject;

public abstract class LocalisateurEntrepots {

    public static void initialise(LocalisateurEntrepots instance) {
        LocalisateurEntrepots.instance = instance;
    }

    public static EntrepotBibliotheques bibliotheques() {
        return instance.getBibliotheques();
    }

    protected abstract EntrepotBibliotheques getBibliotheques();

    @Inject
    private static LocalisateurEntrepots instance;
}
