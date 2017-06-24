package alexandria.command.bibliotheque;


import alexandria.modele.bibliotheque.Emprunt;
import alexandria.modele.lecteur.Lecteur;
import arpinum.command.Command;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SortExemplaireCommande implements Command<Emprunt>{

    public SortExemplaireCommande(String idBibliothèque, Lecteur lecteur, UUID idExemplaire) {
        this.idBibliothèque = idBibliothèque;
        this.lecteur = lecteur;
        this.idExemplaire = idExemplaire;
    }

    @NotEmpty
    public final String idBibliothèque;

    @NotNull
    public final Lecteur lecteur;

    @NotNull
    public final UUID idExemplaire;
}
