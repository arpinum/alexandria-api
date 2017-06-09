package alexandria.saga.bibliiotheque;


import alexandria.command.bibliotheque.AjoutExemplaireCommande;
import alexandria.command.bibliotheque.CreeBibliothequeParDéfautCommande;
import alexandria.modele.bibliotheque.IdentifiantExemplaire;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.command.CommandBus;
import arpinum.saga.SagaHandler;
import io.vavr.Tuple;
import io.vavr.concurrent.Future;

import javax.inject.Inject;

public class AjouteExemplaireBibliothèqueParDéfautSagaCapteur implements SagaHandler<IdentifiantExemplaire, AjouteExemplaireBibliothèqueParDéfautSaga> {

    @Inject
    public AjouteExemplaireBibliothèqueParDéfautSagaCapteur(RegistreLecteurs registreLecteurs) {
        this.registreLecteurs = registreLecteurs;
    }

    @Override
    public Future<IdentifiantExemplaire> run(CommandBus bus, AjouteExemplaireBibliothèqueParDéfautSaga saga) {
        return registreLecteurs.trouve(saga.idLecteur)
                .flatMap(lecteur -> bus.send(new CreeBibliothequeParDéfautCommande(lecteur))
                        .map(id -> Tuple.of(id, lecteur)))
                .flatMap(t -> t.apply((id, lecteur) -> bus.send(new AjoutExemplaireCommande(id, saga.isbn, lecteur))));
    }

    private final RegistreLecteurs registreLecteurs;
}
