package catalogue;


import java.util.List;
import java.util.Optional;

public interface CatalogueLivre {

    Optional<DetailsLivre> parIsbn(String isbn);

    List<DetailsLivre> recherche(String recherche);
}
