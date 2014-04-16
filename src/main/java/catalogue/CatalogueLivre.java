package catalogue;


import java.util.Optional;

public interface CatalogueLivre {

    public Optional<DetailsLivre> parIsbn(String isbn);

    public Iterable<DetailsLivre> recherche(String recherche);
}
