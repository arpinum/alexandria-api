package alexandria.modele.bibliotheque;

import arpinum.ddd.event.Event;

import java.util.UUID;

public class ExemplaireRendu extends Event<Bibliotheque> {

    @SuppressWarnings("unused")
    public ExemplaireRendu() {
    }

    public ExemplaireRendu(String idBibliothèque, UUID idExemplaire) {
        super(idBibliothèque);
        this.idExemplaire = idExemplaire;
    }

    public UUID getIdExemplaire() {
        return idExemplaire;
    }

    private UUID idExemplaire;
}
