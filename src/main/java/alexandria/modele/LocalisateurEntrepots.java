package alexandria.modele;

import alexandria.modele.bibliotheque.EntrepotBibliotheques;

public abstract class LocalisateurEntrepots {

    public static void initialise(LocalisateurEntrepots instance) {
        LocalisateurEntrepots.instance = instance;
    }

    public static EntrepotBibliotheques bibliotheques() {
        return instance.getBibliotheques();
    }

    protected abstract EntrepotBibliotheques getBibliotheques();

    private static LocalisateurEntrepots instance;
}
