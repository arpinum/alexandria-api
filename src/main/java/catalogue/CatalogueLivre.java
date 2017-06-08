package catalogue;


import io.vavr.collection.Seq;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;

import java.util.List;
import java.util.Optional;

public interface CatalogueLivre {

    Future<Option<DetailsLivre>> parIsbn(String isbn);

    Future<Seq<DetailsLivre>> recherche(String recherche);
}
