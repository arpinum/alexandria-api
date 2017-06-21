package alexandria.query.exemplaire.resume.synchronisation;

import alexandria.modele.exemplaire.ExemplaireAjouté;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.ddd.event.EventCaptor;
import org.jongo.Jongo;

import javax.inject.Inject;

public class SurExemplaireAjouté implements EventCaptor<ExemplaireAjouté> {

    @Inject
    public SurExemplaireAjouté(Jongo jongo, RegistreLecteurs registreLecteurs) {
        this.jongo = jongo;
        this.registreLecteurs = registreLecteurs;
    }

    @Override
    public void execute(ExemplaireAjouté évènement) {
        registreLecteurs.ficheDe(évènement.getIdLecteur())
                .peek(ficheLecteur -> {
                    jongo.getCollection("vue_resume_exemplaires")
                            .update("{_id:#}", évènement.getTargetId())
                            .upsert()
                            .with("{$set:{disponible: true, lecteur:#, idBibliothque:#, isbn:#}}", ficheLecteur, évènement.getIdBibliothèque(), évènement.getIsbn());
                });

    }

    private Jongo jongo;
    private RegistreLecteurs registreLecteurs;
}
