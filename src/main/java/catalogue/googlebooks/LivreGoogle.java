package catalogue.googlebooks;

import catalogue.DetailsLivre;

import java.util.Optional;

public class LivreGoogle {

    public DetailsLivre enDetailsLivre() {
        final DetailsLivre résultat = new DetailsLivre();
        résultat.titre = volumeInfo.title;
        résultat.isbn = isbn();
        return résultat;
    }

    private String isbn() {
        return identifiantDeType("ISBN_13").orElseGet(() -> volumeInfo.industryIdentifiers.stream().findFirst().get()).type;
    }

    private Optional<Identifieur> identifiantDeType(String type) {
        return volumeInfo.industryIdentifiers.stream().filter(identifier -> identifier.type.equals(type)).findFirst();
    }

    public VolumeGoogle volumeInfo;
}
