package catalogue.googlebooks;

import catalogue.DetailsLivre;

public class LivreGoogle {

    public DetailsLivre enDetailsLivre() {
        final DetailsLivre résultat = new DetailsLivre();
        résultat.titre = volumeInfo.title;
        return résultat;
    }

    public VolumeGoogle volumeInfo;
}
