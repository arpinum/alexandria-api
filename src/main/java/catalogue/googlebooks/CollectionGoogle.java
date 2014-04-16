package catalogue.googlebooks;

import catalogue.DetailsLivre;

import java.util.List;
import java.util.stream.Collectors;

public class CollectionGoogle {

    public List<DetailsLivre> enDetailsLivres() {
        return items.stream().map( livre -> livre.enDetailsLivre()).collect(Collectors.toList());
    }

    public List<LivreGoogle> items;
}
