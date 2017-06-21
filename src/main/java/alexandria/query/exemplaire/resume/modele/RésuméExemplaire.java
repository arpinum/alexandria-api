package alexandria.query.exemplaire.resume.modele;

import alexandria.modele.lecteur.FicheLecteur;
import org.jongo.marshall.jackson.oid.MongoId;

import java.util.UUID;

public class RésuméExemplaire {

    @MongoId
    public UUID id;

    public String idBibliotheque;

    public String isbn;

    public FicheLecteur lecteur;
}
