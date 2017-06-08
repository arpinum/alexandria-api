package catalogue.googlebooks;

import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
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

public class CatalogueLivreGoogle implements CatalogueLivre {

    public CatalogueLivreGoogle(OkHttpClient client, String apiKey) {
        this.client = client;
        this.apiKey = apiKey;
    }

    @Override
    public Future<Option<DetailsLivre>> parIsbn(String isbn) {
        return recherche("isbn:" + isbn)
                .map(Traversable::headOption);
    }

    @Override
    public Future<Seq<DetailsLivre>> recherche(String recherche) {
        LOGGER.debug("Recherche d'un livre : {}", recherche);
        Request request = new Request.Builder()
                .url(String.format("https://www.googleapis.com/books/v1/volumes?q=%s&key=%s", recherche, apiKey))
                .build();
        Promise<Seq<DetailsLivre>> promise = Promise.make();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LOGGER.error("Impossible de faire la recherche sur google", e);
                promise.failure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    promise.failure(new RuntimeException(String.format("%s: %s", response.code(), response.body().string())));
                    return;
                }
                try (Reader reader = response.body().charStream()) {
                    final CollectionGoogle collectionGoogle = OBJECT_MAPPER.readValue(reader, CollectionGoogle.class);
                    promise.success(collectionGoogle.enDetailsLivres());
                } catch (Exception ex) {
                    promise.failure(ex);
                }
            }
        });
        return promise.future();
    }


    private final OkHttpClient client;
    private final String apiKey;
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogueLivreGoogle.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
}
