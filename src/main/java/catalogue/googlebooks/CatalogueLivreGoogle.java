package catalogue.googlebooks;

import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import com.google.gson.Gson;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Method;
import org.restlet.data.Protocol;

import java.util.List;
import java.util.Optional;

public class CatalogueLivreGoogle implements CatalogueLivre {

    @Override
    public Optional<DetailsLivre> parIsbn(String isbn) {
        return recherche("isbn:" + isbn).stream().findFirst();
    }

    @Override
    public List<DetailsLivre> recherche(String recherche) {
        final Request requête = new Request(Method.GET, String.format("https://www.googleapis.com/books/v1/volumes?q=%s", recherche));
        final Response response = client.handle(requête);
        final CollectionGoogle collectionGoogle = new Gson().fromJson(response.getEntityAsText(), CollectionGoogle.class);
        return collectionGoogle.enDetailsLivres();
    }

    private final Client client = new Client(Protocol.HTTPS);
}
