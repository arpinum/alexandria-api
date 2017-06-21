package alexandria.command.bibliotheque;

import alexandria.modele.exemplaire.IdentifiantExemplaire;
import alexandria.modele.lecteur.Lecteur;
import arpinum.command.Command;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class AjoutExemplaireCommande implements Command<IdentifiantExemplaire> {

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
