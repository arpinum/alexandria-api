package alexandria.query.livre.details.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.query.livre.details.modele.Livre;
import alexandria.query.livre.details.modele.ResumeExemplaire;
import arpinum.ddd.event.EventCaptor;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import io.vavr.control.Option;
import org.jongo.Jongo;

import javax.inject.Inject;

public class SurExemplaireAjoute implements EventCaptor<ExemplaireAjouteEvenement> {

    @Inject
    public SurExemplaireAjoute(Jongo jongo, CatalogueLivre catalogue) {
        this.jongo = jongo;
        this.catalogue = catalogue;
    }

    @Override
    public void execute(ExemplaireAjouteEvenement evenement) {
        final Option<Livre> livreEventuel = Option.of(jongo.getCollection("vue_detailslivre").findOne("{_id:#}", evenement.getIsbn()).as(Livre.class));

        livreEventuel.orElse(() -> creeLivre(evenement))
                .peek(livre -> {
                    livre.exemplaires.add(new ResumeExemplaire(evenement.getIdLecteur(), evenement.getTargetId().toString()));
                    jongo.getCollection("vue_detailslivre").update("{_id:#}", livre.isbn).upsert().with(livre);
                });
    }


    private Option<Livre> creeLivre(ExemplaireAjouteEvenement evenement) {
        return catalogue.parIsbn(evenement.getIsbn())
                .map(l -> l.getOrElse(DetailsLivre.LIVRE_VIDE))
                .map(l -> new Livre(evenement.getIsbn(), l)).toOption();
    }

    private final Jongo jongo;

    private final CatalogueLivre catalogue;
}
