package alexandria.modele.emprunt;

import fr.arpinum.graine.modele.ErreurMetier;

public class ExemplaireDejaEmprunte extends ErreurMetier{

    public ExemplaireDejaEmprunte() {
        super("Exemplaire déjà emprunté");
    }
}
