package alexandria.command.bibliotheque;

import arpinum.command.VoidCommand;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RendExemplaireCommande implements VoidCommand {

    public RendExemplaireCommande(String idBibliotheque, UUID idExemplaire) {
        this.idBibliotheque = idBibliotheque;
        this.idExemplaire = idExemplaire;
    }

    @NotEmpty
    public String idBibliotheque;

    @NotNull
    public UUID idExemplaire;
}
