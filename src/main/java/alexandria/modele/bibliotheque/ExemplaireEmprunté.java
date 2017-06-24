package alexandria.modele.bibliotheque;

import arpinum.ddd.event.Event;

import java.util.UUID;

public class ExemplaireEmprunté extends Event<Bibliotheque> {

    @SuppressWarnings("unused")
    public ExemplaireEmprunté() {
    }

    public ExemplaireEmprunté(String idBibliothèque, UUID idExemplaire, String idLecteur) {
        super(idBibliothèque);
        this.idExemplaire = idExemplaire;
        this.idLecteur = idLecteur;
    }

    public UUID getIdExemplaire() {
        return idExemplaire;
    }

    public String getIdLecteur() {
        return idLecteur;
    }

    private UUID idExemplaire;
    private String idLecteur;
}
