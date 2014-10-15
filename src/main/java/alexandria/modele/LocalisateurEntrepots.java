package alexandria.modele;

import alexandria.modele.bibliotheque.EntrepotBibliotheques;
import alexandria.modele.emprunt.EntrepotEmprunts;

public abstract class LocalisateurEntrepots {

    public static void initialise(LocalisateurEntrepots instance) {
        LocalisateurEntrepots.instance = instance;
    }

    public static EntrepotBibliotheques bibliotheques() {
        return instance.getBibliotheques();
    }

    public static EntrepotEmprunts emprunts() {return instance.getEmprunts();}

    protected abstract EntrepotEmprunts getEmprunts();

    protected abstract EntrepotBibliotheques getBibliotheques();

    private static LocalisateurEntrepots instance;
}
