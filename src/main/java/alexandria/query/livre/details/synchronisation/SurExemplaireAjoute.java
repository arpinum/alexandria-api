package alexandria.query.livre.details.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.modele.lecteur.FicheLecteur;
import alexandria.modele.lecteur.RegistreLecteurs;
import alexandria.query.livre.details.modele.Livre;
import alexandria.query.livre.details.modele.ResumeExemplaire;
import arpinum.ddd.event.EventCaptor;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.concurrent.Future;
import io.vavr.control.Option;
import org.jongo.Jongo;

import javax.inject.Inject;
import java.util.function.Function;

public class SurExemplaireAjoute implements EventCaptor<ExemplaireAjouteEvenement> {

    @Inject
    public SurExemplaireAjoute(Jongo jongo, CatalogueLivre catalogue, RegistreLecteurs registre) {
        this.jongo = jongo;
        this.catalogue = catalogue;
        this.registre = registre;
    }

    @Override
    public void execute(ExemplaireAjouteEvenement evenement) {
        final Option<Livre> livreEventuel = Option.of(jongo.getCollection("vue_detailslivre").findOne("{_id:#}", evenement.getIsbn()).as(Livre.class));

        livreEventuel.orElse(() -> creeLivre(evenement))
                .flatMap(ficheLecteur(evenement))
                .peek(tuple -> {
                    Livre livre = avecExemplaire(evenement, tuple);
                    jongo.getCollection("vue_detailslivre").update("{_id:#}", livre.isbn).upsert().with(livre);
                });
    }

    private Livre avecExemplaire(ExemplaireAjouteEvenement evenement, Tuple2<Livre, FicheLecteur> t) {
        return t.apply((livre, fiche) -> {
            ResumeExemplaire résumé = new ResumeExemplaire(fiche, evenement.getTargetId().toString());
            livre.exemplaires.add(résumé);
            return livre;
        });
    }

    private Function<Livre, Option<? extends Tuple2<Livre, FicheLecteur>>> ficheLecteur(ExemplaireAjouteEvenement evenement) {
        return l -> ficheLecteur(evenement.getIdLecteur()).map(f -> Tuple.of(l, f)).toOption();
    }

    private Future<FicheLecteur> ficheLecteur(String id) {
        return registre.ficheDe(id);
    }

    private Option<Livre> creeLivre(ExemplaireAjouteEvenement evenement) {
        return catalogue.parIsbn(evenement.getIsbn())
                .map(l -> l.getOrElse(DetailsLivre.LIVRE_VIDE))
                .map(l -> new Livre(evenement.getIsbn(), l)).toOption();
    }

    private final Jongo jongo;

    private final CatalogueLivre catalogue;
    private RegistreLecteurs registre;
}
