package alexandria.command.bibliotheque;

import alexandria.modele.lecteur.Lecteur;
import arpinum.command.Command;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

public class AjoutExemplaireCommande implements Command<UUID> {

    @NotEmpty
    public String idBibliotheque;

    @NotEmpty
    public String isbn;

    @NotEmpty
    public Lecteur lecteur;
}
