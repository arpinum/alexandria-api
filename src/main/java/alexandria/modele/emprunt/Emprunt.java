package alexandria.modele.emprunt;

import alexandria.modele.bibliotheque.Exemplaire;
import fr.arpinum.graine.modele.RacineAvecUuid;

import java.util.UUID;

public class Emprunt implements RacineAvecUuid {

    protected Emprunt() {
        //pour mongolink
    }

    public Emprunt(Exemplaire exemplaire, String identifiantLecteur) {
        this.exemplaire = exemplaire;
        this.identifiantLecteur = identifiantLecteur;
        identifiant = UUID.randomUUID();
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public String getIdentifiantLecteur() {
        return identifiantLecteur;
    }

    @Override
    public UUID getId() {
        return identifiant;
    }

    private Exemplaire exemplaire;
    private String identifiantLecteur;
    private UUID identifiant;
}
