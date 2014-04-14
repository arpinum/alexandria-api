package alexandria.modele.bibliotheque;

import alexandria.modele.lecteur.Lecteur;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import fr.arpinum.graine.modele.RacineAvecUuid;

import java.util.List;
import java.util.UUID;

public class Bibliotheque implements RacineAvecUuid {

    public Bibliotheque(Lecteur lecteur) {
        emailLecteur = lecteur.getEmail();
        exemplaires = Lists.newArrayList();
        identifiant = UUID.randomUUID();
    }

    @Override
    public UUID getId() {
        return identifiant;
    }

    public String emailLecteur() {
        return emailLecteur;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass()).add("id", getId()).toString();
    }

    public Exemplaire ajouteExemplaire(String isbn) {
        final Exemplaire exemplaire = new Exemplaire(isbn);
        exemplaires.add(exemplaire);
        return  exemplaire;
    }

    public boolean contient(Exemplaire exemplaire) {
        return exemplaires.contains(exemplaire);
    }

    private final UUID identifiant;

    private String emailLecteur;

    private List<Exemplaire> exemplaires;
}
