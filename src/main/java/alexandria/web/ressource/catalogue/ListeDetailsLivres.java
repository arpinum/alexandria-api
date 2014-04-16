package alexandria.web.ressource.catalogue;

import catalogue.DetailsLivre;

import java.util.List;

public class ListeDetailsLivres {
    public static ListeDetailsLivres nouvelle(List<DetailsLivre> liste) {
        return new ListeDetailsLivres(liste);
    }

    public ListeDetailsLivres(List<DetailsLivre> livres) {
        this.livres = livres;
    }

    public final List<DetailsLivre> livres;
}
