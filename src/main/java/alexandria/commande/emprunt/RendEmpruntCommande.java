package alexandria.commande.emprunt;

import fr.arpinum.graine.commande.Commande;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class RendEmpruntCommande implements Commande<Void> {

    @NotEmpty
    public String isbn;

    @NotNull
    public UUID idBibliotheque;
}
