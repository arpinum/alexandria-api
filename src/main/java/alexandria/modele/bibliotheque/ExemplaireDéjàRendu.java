package alexandria.modele.bibliotheque;

import arpinum.ddd.BusinessError;

public class ExemplaireDéjàRendu extends BusinessError {
    public ExemplaireDéjàRendu() {
        super("EXEMPLAIRE_DEJA_RENDU");
    }
}
