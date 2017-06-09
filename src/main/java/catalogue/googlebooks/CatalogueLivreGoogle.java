package catalogue.googlebooks;

import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.vavr.Function1;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.concurrent.Future;
import io.vavr.concurrent.Promise;
import io.vavr.control.Option;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

public class CatalogueLivreGoogle implements CatalogueLivre {


    public CatalogueLivreGoogle(ExecutorService executorService, OkHttpClient client, String apiKey) {
        this.executorService = executorService;
        this.client = client;
        this.apiKey = apiKey;
        cache = Function1.of(this::executeRecherche).memoized();
    }
    @Override
    public Future<Option<DetailsLivre>> parIsbn(String isbn) {
        return recherche("isbn:" + isbn)
                .map(Traversable::headOption);
    }

    @Override
    public Future<Seq<DetailsLivre>> recherche(String recherche) {
        return cache.apply(recherche);
    }

    private Future<Seq<DetailsLivre>> executeRecherche(String recherche) {
        LOGGER.debug("Recherche d'un livre : {}", recherche);
        Request request = new Request.Builder()
                .url(String.format("https://www.googleapis.com/books/v1/volumes?q=%s&key=%s", recherche, apiKey))
                .build();
        return Future.ofCallable(executorService, () -> {
            Response response = client.newCall(request).execute();
            LOGGER.debug("Google response {}", response.code());
            if (!response.isSuccessful()) {
                throw new RuntimeException(String.format("%s: %s", response.code(), response.body().string()));
            }
            try (Reader reader = response.body().charStream()) {
                final CollectionGoogle collectionGoogle = OBJECT_MAPPER.readValue(reader, CollectionGoogle.class);
                return collectionGoogle.enDetailsLivres();
            }
        });
    }

    private ExecutorService executorService;

    private final OkHttpClient client;
    private final String apiKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogueLivreGoogle.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Function1<String, Future<Seq<DetailsLivre>>> cache;
}
