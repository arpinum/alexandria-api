package alexandria.query.livre.resume.synchronisation;

import alexandria.modele.exemplaire.ExemplaireAjouté;
import arpinum.ddd.event.EventCaptor;
import catalogue.CatalogueLivre;
import catalogue.DetailsLivre;
import org.jongo.Jongo;

import javax.inject.Inject;

public class SurExemplaireAjoute implements EventCaptor<ExemplaireAjouté> {

    @Inject
    public SurExemplaireAjoute(Jongo jongo, CatalogueLivre catalogue) {
        this.jongo = jongo;
        this.catalogue = catalogue;
    }

    @Override
    public void execute(ExemplaireAjouté evenement) {
        catalogue.parIsbn(evenement.getIsbn())
                .map(o -> o.getOrElse(DetailsLivre.LIVRE_VIDE))
                .onSuccess(d -> jongo.getCollection("vue_resumelivre")
                        .update("{_id:#}", evenement.getIsbn())
                        .upsert()
                        .with("{$inc:{nombre:1}, $set:{titre:#, description:#,image:#}}"
                                , d.titre
                                , d.description
                                , d.image));


    }

    private final Jongo jongo;

    private final CatalogueLivre catalogue;
}
