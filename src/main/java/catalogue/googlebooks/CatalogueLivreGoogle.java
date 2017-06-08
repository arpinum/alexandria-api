package catalogue.googlebooks;

import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            LOGGER.error("Impossible de faire la recherche sur google", e);
            return Lists.newArrayList();
        }
    }

    private List<DetailsLivre> faisRecherche(String recherche) throws IOException {
        LOGGER.debug("Recherche d'un livre : {}", recherche);
        final URL url = new URL(String.format("https://www.googleapis.com/books/v1/volumes?q=%s", recherche));
        try (Reader reader = Resources.asCharSource(url, Charsets.UTF_8).openStream()) {
            final CollectionGoogle collectionGoogle = OBJECT_MAPPER.readValue(reader, CollectionGoogle.class);
            return collectionGoogle.enDetailsLivres();
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogueLivreGoogle.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
}
