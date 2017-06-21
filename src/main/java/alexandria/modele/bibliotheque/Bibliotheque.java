package alexandria.modele.bibliotheque;

import alexandria.modele.exemplaire.*;
import alexandria.modele.lecteur.Lecteur;
import arpinum.ddd.BaseAggregate;
import arpinum.ddd.event.EventSourceHandler;
import com.google.common.base.MoreObjects;
import io.vavr.*;

import java.util.UUID;


public class Bibliotheque extends BaseAggregate<String> {

    public static Fabrique fabrique() {
        return new Fabrique();
    }

    @SuppressWarnings("UnusedDeclaration")
    public Bibliotheque() {

    }

    public Bibliotheque(String id, Lecteur lecteur) {
        setId(id);
        idLecteur = lecteur.getId();
    }

    public String idLecteur() {
        return idLecteur;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass()).add("getId", getId()).toString();
    }

    public Tuple2<ExemplaireAjouté, Exemplaire> ajouteExemplaire(String isbn) {
        final Exemplaire exemplaire = new Exemplaire(UUID.randomUUID(), isbn, this.getId());
        return Tuple.of(nouvelExemplaire(exemplaire.getId(), isbn), exemplaire);
    }

    private ExemplaireAjouté nouvelExemplaire(UUID id, String isbn) {
        return new ExemplaireAjouté(id, getId(), idLecteur, isbn);
    }

    private String idLecteur;

    @EventSourceHandler
    public void rejoue(BibliothequeCréée évènement) {
        setId(évènement.getTargetId().toString());
        idLecteur = évènement.idLecteur();
    }

    public static class Fabrique {

        public Tuple2<BibliothequeCréée, Bibliotheque> pour(Lecteur lecteur) {
            return Tuple.of(new BibliothequeCréée(lecteur.getId(), lecteur), new Bibliotheque(lecteur.getId(), lecteur));
        }
    }

}
