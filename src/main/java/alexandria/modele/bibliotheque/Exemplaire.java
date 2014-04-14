package alexandria.modele.bibliotheque;

import java.util.Objects;

public class Exemplaire {

    protected Exemplaire() {
        // pour mongolink
    }

    public Exemplaire(String isbn) {
        this.isbn = isbn;
    }

    public String isbn() {
        return isbn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(isbn, ((Exemplaire) o).isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    private  String isbn;
}
