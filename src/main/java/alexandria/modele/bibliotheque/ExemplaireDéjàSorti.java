package alexandria.modele.bibliotheque;

import arpinum.ddd.BusinessError;

public class ExemplaireDéjàSorti extends BusinessError {

    public ExemplaireDéjàSorti() {
        super("EXEMPLAIRE_DEJA_SORTI");
    }
}
