package alexandria.saga.bibliiotheque;

import alexandria.modele.bibliotheque.Emprunt;
import arpinum.saga.Saga;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SortExemplaireSaga implements Saga<Emprunt> {

    @NotEmpty
    public String idLecteur;

    @NotEmpty
    public String idBibliotheque;

    @NotNull
    public UUID idExemplaire;
}
