package alexandria.web.ressource.lecteur;

import alexandria.saga.bibliiotheque.SortExemplaireSaga;
import arpinum.command.CommandBus;
import arpinum.configuration.Secured;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/lecteur/exemplaires")
@Secured
public class LecteurEmpruntsRessource {

    @Inject
    public LecteurEmpruntsRessource(CommandBus bus) {
        this.bus = bus;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void sort(@Suspended AsyncResponse response, @Context SecurityContext context, SortExemplaireSaga payload) {
        payload.idLecteur = context.getUserPrincipal().getName();
        bus.send(payload)
                .onSuccess(response::resume)
                .onFailure(response::resume);
    }

    private CommandBus bus;
}
