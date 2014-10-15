package alexandria.modele.emprunt;

import fr.arpinum.graine.modele.ErreurMetier;

public class ExemplaireInconnu extends ErreurMetier {

    public ExemplaireInconnu() {
        super("L'exemplaire n'est pas dans cette bibliothèque");
    }
}
