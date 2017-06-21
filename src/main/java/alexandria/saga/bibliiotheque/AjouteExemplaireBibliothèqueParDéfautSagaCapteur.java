package alexandria.saga.bibliiotheque;


import alexandria.command.bibliotheque.*;
import alexandria.modele.lecteur.*;
import arpinum.command.CommandBus;
import arpinum.saga.SagaHandler;
import io.vavr.*;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.UUID;
import java.util.function.Function;

public class AjouteExemplaireBibliothèqueParDéfautSagaCapteur implements SagaHandler<UUID, AjouteExemplaireBibliothèqueParDéfautSaga> {

    @Inject
    public AjouteExemplaireBibliothèqueParDéfautSagaCapteur(RegistreLecteurs registreLecteurs) {
        this.registreLecteurs = registreLecteurs;
    }

    @Override
    public Future<UUID> run(CommandBus bus, AjouteExemplaireBibliothèqueParDéfautSaga saga) {
        return registreLecteurs.trouve(saga.idLecteur)
                .flatMap(créeOuTrouveBibliothèqueParDéfaut(bus))
                .flatMap(ajouteExemplaire(bus, saga));
    }

    private Function<Lecteur, Future<? extends Tuple2<String, Lecteur>>> créeOuTrouveBibliothèqueParDéfaut(CommandBus bus) {
        return lecteur -> bus.send(new CreeBibliothequeParDéfautCommande(lecteur))
                .map(id -> Tuple.of(id, lecteur));
    }

    private Function<Tuple2<String, Lecteur>, Future<UUID>> ajouteExemplaire(CommandBus bus, AjouteExemplaireBibliothèqueParDéfautSaga saga) {
        return t -> t.apply((id, lecteur) -> bus.send(new AjoutExemplaireCommande(id, saga.isbn, lecteur)));
    }

    private final RegistreLecteurs registreLecteurs;
}
