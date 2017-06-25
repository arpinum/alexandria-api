package alexandria.query.exemplaire.resume.synchronisation;

import alexandria.modele.bibliotheque.ExemplaireRendu;
import arpinum.ddd.event.EventCaptor;
import org.jongo.Jongo;

import javax.inject.*;

public class SurExemplaireRendu implements EventCaptor<ExemplaireRendu> {

    @Inject
    public SurExemplaireRendu(Provider<Jongo> jongo) {
        this.jongo = jongo;
    }

    @Override
    public void execute(ExemplaireRendu exemplaireRendu) {
        jongo.get().getCollection("vue_resume_exemplaires")
                .update("{_id:#}", exemplaireRendu.getIdExemplaire())
                .with("{$set:{disponible:true}}");
    }

    private Provider<Jongo> jongo;
}
