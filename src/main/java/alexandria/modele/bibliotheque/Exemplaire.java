package alexandria.modele.bibliotheque;

import com.google.common.base.Objects;
import java.util.UUID;

public class Exemplaire {

    protected Exemplaire() {
        // pour mongolink
    }

    public Exemplaire(String isbn, UUID identifiantBibliotheque) {
        this.isbn = isbn;
        this.identifiantBibliotheque = identifiantBibliotheque;

    }

    public String isbn() {
        return isbn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Exemplaire autre = (Exemplaire) o;
        return Objects.equal(isbn, autre.isbn) && Objects.equal(identifiantBibliotheque, autre.identifiantBibliotheque);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn, identifiantBibliotheque);
    }

    public UUID identifiantBibliotheque() {
        return this.identifiantBibliotheque;
    }

    private String isbn;
    private UUID identifiantBibliotheque;
}
