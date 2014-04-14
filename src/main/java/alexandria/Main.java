package alexandria;

import alexandria.web.AlexandriaApplication;
import fr.arpinum.graine.web.restlet.Serveur;
import org.restlet.Context;

import java.util.Optional;

public class Main {


    public static void main(String[] args) throws Exception {
        new Serveur(new AlexandriaApplication(new Context())).start(port());
    }

    private static int port() {
        final Optional<String> port = Optional.ofNullable(System.getenv("PORT"));
        return Integer.parseInt(port.orElse("8080"));
    }
}
