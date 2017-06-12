package authentification.web;

import authentification.application.UtilisateurService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/tokens")
public class TokensRessource {

    @Inject
    public TokensRessource(UtilisateurService service) {
        this.service = service;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String authentifie(Payload payload) {
        return service.authentifie(payload.email, payload.prénom, payload.nom);
    }


    private UtilisateurService service;

    public static class Payload {
        public String email;
        public String prénom;
        public String nom;
    }
}
