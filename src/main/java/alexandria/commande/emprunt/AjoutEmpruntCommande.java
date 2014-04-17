package alexandria.commande.emprunt;

import fr.arpinum.graine.commande.Commande;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AjoutEmpruntCommande implements Commande<UUID> {

    @NotNull
    @Email
    public String email;

    @NotNull
    public String isbn;
}
