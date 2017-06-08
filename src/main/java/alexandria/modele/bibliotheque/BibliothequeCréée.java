package alexandria.modele.bibliotheque;


import alexandria.modele.lecteur.Lecteur;
import arpinum.ddd.event.Event;

public class BibliothequeCréée extends Event<Bibliotheque> {
    @SuppressWarnings("unused")
    public BibliothequeCréée() {
    }

    public BibliothequeCréée(String targetId, Lecteur lecteur) {
        super(targetId);
        this.idLecteur = lecteur.getId();
    }

    public String idLecteur() {
        return idLecteur;
    }

    private String idLecteur;
}
