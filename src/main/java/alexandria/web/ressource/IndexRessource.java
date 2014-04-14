package alexandria.web.ressource;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class IndexRessource extends ServerResource {

    @Get
    public String représente() {
        return "Hello world";
    }
}
