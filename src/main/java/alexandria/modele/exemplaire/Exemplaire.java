package alexandria.modele.exemplaire;

import arpinum.ddd.BaseAggregateWithUuid;
import arpinum.ddd.event.EventSourceHandler;

import java.util.UUID;

public class Exemplaire extends BaseAggregateWithUuid{

    protected Exemplaire() {
    }

    public Exemplaire(UUID id, String isbn, String idBibliothèque) {
        setId(id);
        this.isbn = isbn;
        this.idBibliothèque = idBibliothèque;
    }

    public String isbn() {
        return isbn;
    }

    public String idBibliothèque() {
        return idBibliothèque;
    }

    public IdentifiantExemplaire identifiant() {
        return new IdentifiantExemplaire(idBibliothèque, isbn);
    }

    @EventSourceHandler
    public void rejoue(ExemplaireAjouté évènement) {
        setId((UUID) évènement.getTargetId());
        isbn = évènement.getIsbn();
        idBibliothèque = évènement.getIdBibliothèque();
    }


    private String isbn;

    private String idBibliothèque;
}
