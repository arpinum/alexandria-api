package alexandria.recherche.livre.synchronisation;

import alexandria.modele.LocalisateurEntrepots;
import alexandria.modele.bibliotheque.Bibliotheque;
import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.recherche.livre.Livre;
import alexandria.recherche.livre.ResumeExemplaire;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import fr.arpinum.graine.modele.evenement.CapteurEvenement;
import org.jongo.Jongo;

import javax.inject.Inject;
import java.util.Optional;
import java.util.function.Supplier;

public class SynchronisationDetailsLivre implements CapteurEvenement<ExemplaireAjouteEvenement>{

    @Inject
    public SynchronisationDetailsLivre(Jongo jongo, CatalogueLivre catalogue) {
        this.jongo = jongo;
        this.catalogue = catalogue;
    }

    @Override
    public void executeEvenement(ExemplaireAjouteEvenement evenement) {
        final Bibliotheque bibliotheque = LocalisateurEntrepots.bibliotheques().get(evenement.idBibliotheque);
        final Optional<Livre> livreEventuel = Optional.ofNullable(jongo.getCollection("vue_detailslivre").findOne("{_id:#}", evenement.isbn).as(Livre.class));
        Livre livre = livreEventuel.orElseGet(creeLivre(evenement));
        livre.exemplaires.add(new ResumeExemplaire(bibliotheque.emailLecteur()));
        jongo.getCollection("vue_detailslivre").update("{_id:#}", livre.isbn).upsert().with(livre);

    }

    private Supplier<? extends Livre> creeLivre(ExemplaireAjouteEvenement evenement) {
        return () -> new Livre(evenement.isbn, catalogue.parIsbn(evenement.isbn).orElse(DetailsLivre.LIVRE_VIDE));
    }

    private final Jongo jongo;
    private final CatalogueLivre catalogue;
}
