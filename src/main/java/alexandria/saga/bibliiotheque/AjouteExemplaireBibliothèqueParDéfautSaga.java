package alexandria.saga.bibliiotheque;

import arpinum.saga.Saga;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;


public class AjouteExemplaireBibliothèqueParDéfautSaga implements Saga<UUID> {

    @NotEmpty
    public String idLecteur;

    @NotEmpty
    public String isbn;
}
