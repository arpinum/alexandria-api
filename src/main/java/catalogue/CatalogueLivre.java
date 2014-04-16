package catalogue;


import java.util.List;
import java.util.Optional;

public interface CatalogueLivre {

    public Optional<DetailsLivre> parIsbn(String isbn);

    public List<DetailsLivre> recherche(String recherche);
}
