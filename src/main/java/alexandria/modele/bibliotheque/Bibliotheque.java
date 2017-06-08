package alexandria.modele.bibliotheque;

import alexandria.modele.lecteur.Lecteur;
import arpinum.ddd.BaseAggregate;
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
    protected Bibliotheque() {

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
        return MoreObjects.toStringHelper(getClass()).add("id", getId()).toString();
    }

    public Tuple2<ExemplaireAjouteEvenement, Exemplaire> ajouteExemplaire(Lecteur lecteur, String isbn) {
        final Exemplaire exemplaire = new Exemplaire(isbn, this.getId());
        exemplaires = exemplaires.append(exemplaire);
        return Tuple.of(nouvelExemplaire(isbn), exemplaire);
    }

    private ExemplaireAjouteEvenement nouvelExemplaire(String isbn) {
        return new ExemplaireAjouteEvenement(this.getId(), isbn);
    }

    public boolean contient(Exemplaire exemplaire) {
        return exemplaires.contains(exemplaire);
    }

    public Option<Exemplaire> trouve(String isbn) {
        return exemplaires.find(e -> e.isbn().equals(isbn));
    }

    private String idLecteur;

    private List<Exemplaire> exemplaires = List.empty();

    public static class Fabrique {

        public Tuple2<BibliothequeCréée, Bibliotheque> pour(Lecteur lecteur) {
            return Tuple.of(new BibliothequeCréée(lecteur.getId(), lecteur), new Bibliotheque(lecteur.getId(), lecteur));
        }
    }

}
