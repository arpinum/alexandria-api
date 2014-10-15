package alexandria.commande.emprunt;

import fr.arpinum.graine.commande.Commande;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class EmpruntExemplaireCommande implements Commande<UUID> {

    @NotEmpty
    @Email
    public String emailLecteur;

    @NotNull
    public UUID identifiantBibliotheque;

    @NotEmpty
    public String isbn;
}
