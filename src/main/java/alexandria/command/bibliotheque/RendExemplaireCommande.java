package alexandria.command.bibliotheque;

import arpinum.command.VoidCommand;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RendExemplaireCommande implements VoidCommand {

    public RendExemplaireCommande(String idBibliothèque, UUID idExemplaire) {
        this.idBibliothèque = idBibliothèque;
        this.idExemplaire = idExemplaire;
    }

    @NotEmpty
    public final String idBibliothèque;

    @NotNull
    public final UUID idExemplaire;
}
