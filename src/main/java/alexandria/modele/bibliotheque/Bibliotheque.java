package alexandria.modele.bibliotheque;

import alexandria.modele.lecteur.Lecteur;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import fr.arpinum.graine.modele.RacineAvecUuid;
import fr.arpinum.graine.modele.evenement.BusEvenement;

import java.util.*;

public class Bibliotheque implements RacineAvecUuid {

    @SuppressWarnings("UnusedDeclaration")
    protected Bibliotheque() {
        // pour mongolink
    }

    public Bibliotheque(Lecteur lecteur) {
        emailLecteur = lecteur.getEmail();
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
        final Exemplaire exemplaire = new Exemplaire(isbn, this.getId());
        exemplaires.add(exemplaire);
        publieEvenement(isbn);
        return exemplaire;
    }

    private void publieEvenement(String isbn) {
        BusEvenement.INSTANCE().publie(new ExemplaireAjouteEvenement(isbn, this.getId()));
    }

    public boolean contient(Exemplaire exemplaire) {
        return exemplaires.contains(exemplaire);
    }

    public Optional<Exemplaire> trouve(String isbn) {
        return exemplaires.stream().filter(e -> e.isbn().equals(isbn)).findFirst();
    }

    private UUID identifiant;

    private String emailLecteur;

    private List<Exemplaire> exemplaires = Lists.newArrayList();

}
