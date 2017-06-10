package alexandria.modele.bibliotheque;

import alexandria.modele.lecteur.Lecteur;
import arpinum.ddd.BaseAggregate;
import arpinum.ddd.event.EventSourceHandler;
import com.google.common.base.MoreObjects;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.control.Option;


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

    public Tuple2<ExemplaireAjouteEvenement, Exemplaire> ajouteExemplaire(String isbn) {
        final Exemplaire exemplaire = new Exemplaire(isbn, this.getId());
        exemplaires = exemplaires.append(exemplaire);
        return Tuple.of(nouvelExemplaire(isbn), exemplaire);
    }

    private ExemplaireAjouteEvenement nouvelExemplaire(String isbn) {
        return new ExemplaireAjouteEvenement(this.getId(), idLecteur, isbn);
    }

    public boolean contient(Exemplaire exemplaire) {
        return exemplaires.contains(exemplaire);
    }

    public Option<Exemplaire> trouve(String isbn) {
        return exemplaires.find(e -> e.isbn().equals(isbn));
    }

    private String idLecteur;

    private List<Exemplaire> exemplaires = List.empty();

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
