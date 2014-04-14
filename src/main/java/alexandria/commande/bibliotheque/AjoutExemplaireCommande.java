package alexandria.commande.bibliotheque;

import fr.arpinum.graine.commande.Commande;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.UUID;

public class AjoutExemplaireCommande implements Commande<UUID> {

    @NotEmpty
    @Email
    public String email;

    @NotEmpty
    public String isbn;
}
