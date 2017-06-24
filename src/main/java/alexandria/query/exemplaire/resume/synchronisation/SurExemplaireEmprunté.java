package alexandria.query.exemplaire.resume.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireEmprunté;
import arpinum.ddd.event.EventCaptor;
import org.jongo.Jongo;

import javax.inject.Inject;
import javax.inject.Provider;

public class SurExemplaireEmprunté implements EventCaptor<ExemplaireEmprunté> {

    @Inject
    public SurExemplaireEmprunté(Provider<Jongo> jongo) {
        this.jongo = jongo;
    }

    @Override
    public void execute(ExemplaireEmprunté évènement) {
        jongo.get().getCollection("vue_resume_exemplaires")
                .update("{_id:#}", évènement.getIdExemplaire())
                .with("{$set:{disponible:false}}");
    }

    private Provider<Jongo> jongo;
}
