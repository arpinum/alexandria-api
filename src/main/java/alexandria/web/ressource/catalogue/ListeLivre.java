package alexandria.web.ressource.catalogue;

import catalogue.DetailsLivre;

import java.util.List;

public class ListeLivre {
    public static ListeLivre nouvelle(List<DetailsLivre> liste) {
        return new ListeLivre(liste);
    }

    public ListeLivre(List<DetailsLivre> livres) {
        this.livres = livres;
    }

    public final List<DetailsLivre> livres;
}
