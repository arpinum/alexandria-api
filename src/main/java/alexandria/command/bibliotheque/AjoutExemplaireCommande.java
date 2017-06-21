package alexandria.command.bibliotheque;

import alexandria.modele.lecteur.Lecteur;
import arpinum.command.Command;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AjoutExemplaireCommande implements Command<UUID> {

    @SuppressWarnings("unused")
    public AjoutExemplaireCommande() {
    }

    public AjoutExemplaireCommande(String idBibliotheque, String isbn, Lecteur lecteur) {
        this.idBibliotheque = idBibliotheque;
        this.isbn = isbn;
        this.lecteur = lecteur;
    }

    @NotEmpty
    public String idBibliotheque;

    @NotEmpty
    public String isbn;

    @NotNull
    public Lecteur lecteur;
}
