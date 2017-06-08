package catalogue.googlebooks;

import catalogue.DetailsLivre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.vavr.collection.Seq;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionGoogle {

    public Seq<DetailsLivre> enDetailsLivres() {
        return io.vavr.collection.List.ofAll(items)
                .map(LivreGoogle::enDetailsLivre);
    }

    public List<LivreGoogle> items;
}
