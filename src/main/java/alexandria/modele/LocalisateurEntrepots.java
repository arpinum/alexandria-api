package alexandria.modele;

import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.emprunt.EntrepotEmprunt;

public abstract class LocalisateurEntrepots {

    public static void initialise(LocalisateurEntrepots instance) {
        LocalisateurEntrepots.instance = instance;
    }

    public static EntrepotBibliotheques bibliotheques() {
        return instance.getBibliotheques();
    }

    protected abstract EntrepotBibliotheques getBibliotheques();

    public static EntrepotEmprunt emprunts() {return instance.getEmprunt();}

    protected abstract EntrepotEmprunt getEmprunt();

    private static LocalisateurEntrepots instance;
}
