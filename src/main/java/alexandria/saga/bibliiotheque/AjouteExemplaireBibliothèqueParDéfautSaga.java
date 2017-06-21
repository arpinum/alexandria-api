package alexandria.saga.bibliiotheque;

import alexandria.modele.exemplaire.IdentifiantExemplaire;
import arpinum.saga.Saga;
import org.hibernate.validator.constraints.NotEmpty;


public class AjouteExemplaireBibliothèqueParDéfautSaga implements Saga<IdentifiantExemplaire> {

    @NotEmpty
    public String idLecteur;

    @NotEmpty
    public String isbn;
}
