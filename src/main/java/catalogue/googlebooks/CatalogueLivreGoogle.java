package catalogue.googlebooks;

import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class CatalogueLivreGoogle implements CatalogueLivre {

    @Override
    public Optional<DetailsLivre> parIsbn(String isbn) {
        return recherche("isbn:" + isbn).stream().findFirst();
    }

    @Override
    public List<DetailsLivre> recherche(String recherche) {
        try {
            return faisRecherche(recherche);
        } catch (java.io.IOException e) {
            return Lists.newArrayList();
        }
    }

    private List<DetailsLivre> faisRecherche(String recherche) throws IOException {
        final URL url = new URL(String.format("https://www.googleapis.com/books/v1/volumes?q=%s", recherche));
        try (Reader reader = Resources.asCharSource(url, Charsets.UTF_8).openStream()) {
            final CollectionGoogle collectionGoogle = new Gson().fromJson(reader, CollectionGoogle.class);
            return collectionGoogle.enDetailsLivres();
        }
    }

}
