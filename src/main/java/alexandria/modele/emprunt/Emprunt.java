package alexandria.modele.emprunt;

import alexandria.modele.bibliotheque.Exemplaire;
import alexandria.modele.lecteur.Lecteur;
import fr.arpinum.graine.modele.RacineAvecUuid;
import fr.arpinum.graine.modele.evenement.BusEvenement;

import java.util.Date;
import java.util.UUID;

public class Emprunt implements RacineAvecUuid {

    @SuppressWarnings("UnusedDeclaration")
    public Emprunt() {
        // pour mongolink
    }

    public Emprunt(Lecteur lecteur, Exemplaire exemplaire) {
        emailLecteur = lecteur.getEmail();
        this.exemplaire = exemplaire;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getEmailLecteur() {
        return emailLecteur;
    }

    public Exemplaire getExemplaire() {
        return exemplaire;
    }

    public boolean rendu() {
        return dateRemise != null;
    }

    public void rend() {
        this.dateRemise = new Date();
        BusEvenement.INSTANCE().publie(new EmpruntRenduEvenement(getId()));
    }


    private String emailLecteur;
    private Exemplaire exemplaire;
    private UUID id = UUID.randomUUID();
    private Date dateRemise;
}
