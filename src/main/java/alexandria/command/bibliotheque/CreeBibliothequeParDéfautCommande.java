package alexandria.command.bibliotheque;


import alexandria.modele.lecteur.Lecteur;
import arpinum.command.Command;

import javax.validation.constraints.NotNull;

public class CreeBibliothequeParDéfautCommande implements Command<String> {

    public CreeBibliothequeParDéfautCommande(Lecteur lecteur) {
        this.lecteur = lecteur;
    }

    @NotNull
    public final Lecteur lecteur;
}
