package alexandria.query.livre.details.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireAjouteEvenement;
import alexandria.query.livre.details.modele.Livre;
import arpinum.ddd.event.EventCaptor;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import io.vavr.concurrent.Future;
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
        //final Bibliotheque bibliotheque = LocalisateurEntrepots.bibliotheques().get(evenement.getIdBibliotheque());
        //final Optional<Livre> livreEventuel = Optional.ofNullable(jongo.getCollection("vue_detailslivre").findOne("{_id:#}", evenement.getIsbn()).as(Livre.class));
        //Livre livre = livreEventuel.orElseGet(creeLivre(evenement));
        //livre.exemplaires.add(new ResumeExemplaire(bibliotheque));
        //jongo.getCollection("vue_detailslivre").update("{_id:#}", livre.isbn).upsert().with(livre);
    }


    private Future<Livre> creeLivre(ExemplaireAjouteEvenement evenement) {
        return catalogue.parIsbn(evenement.getIsbn())
                .map(l -> l.getOrElse(DetailsLivre.LIVRE_VIDE))
                .map(l -> new Livre(evenement.getIsbn(), l));
    }

    private final Jongo jongo;

    private final CatalogueLivre catalogue;
}
