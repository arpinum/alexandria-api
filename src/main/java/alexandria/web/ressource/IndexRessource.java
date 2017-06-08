package alexandria.web.ressource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class IndexRessource {

    @GET
    public String représente() {
        return "Hello world";
    }
}
