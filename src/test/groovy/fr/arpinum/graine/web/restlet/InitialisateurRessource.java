package fr.arpinum.graine.web.restlet;

import com.google.common.base.Joiner;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.ServerResource;

public class InitialisateurRessource {

    public static InitialisateurRessource pour(ServerResource ressource) {
        return new InitialisateurRessource(ressource);
    }

    private InitialisateurRessource(ServerResource ressource) {
        this.ressource = ressource;
    }

    public InitialisateurRessource avecParamètre(String clef, Object valeur) {
        request.getAttributes().put(clef, valeur);
        return this;
    }

    public InitialisateurRessource avecQuery(String clef, String valeur) {
        query = Joiner.on("&").join(query, String.format("%s=%s", clef, valeur));
        return this;
    }

    public void initialise() {
        request.setResourceRef("http:localhost?" + query);
        ressource.init(new Context(), request, new Response(request));
    }

    private final ServerResource ressource;
    private String query = "";
    private final Request request = new Request();

}
