package alexandria.modele.lecteur;

import java.util.Objects;

public class Lecteur {

    public Lecteur(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecteur lecteur = (Lecteur) o;
        return Objects.equals(id, lecteur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
