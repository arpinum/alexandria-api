package alexandria.saga.bibliiotheque;


import alexandria.command.bibliotheque.AjoutExemplaireCommande;
import alexandria.command.bibliotheque.CreeBibliothequeParDéfautCommande;
import alexandria.modele.bibliotheque.IdentifiantExemplaire;
import alexandria.modele.lecteur.Lecteur;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.command.CommandBus;
import arpinum.saga.SagaHandler;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.function.Function;

public class AjouteExemplaireBibliothèqueParDéfautSagaCapteur implements SagaHandler<IdentifiantExemplaire, AjouteExemplaireBibliothèqueParDéfautSaga> {

    @Inject
    public AjouteExemplaireBibliothèqueParDéfautSagaCapteur(RegistreLecteurs registreLecteurs) {
        this.registreLecteurs = registreLecteurs;
    }

    @Override
    public Future<IdentifiantExemplaire> run(CommandBus bus, AjouteExemplaireBibliothèqueParDéfautSaga saga) {
        return registreLecteurs.trouve(saga.idLecteur)
                .flatMap(créeOuTrouveBibliothèqueParDéfaut(bus))
                .flatMap(ajouteExemplaire(bus, saga));
    }

    private Function<Lecteur, Future<? extends Tuple2<String, Lecteur>>> créeOuTrouveBibliothèqueParDéfaut(CommandBus bus) {
        return lecteur -> bus.send(new CreeBibliothequeParDéfautCommande(lecteur))
                .map(id -> Tuple.of(id, lecteur));
    }

    private Function<Tuple2<String, Lecteur>, Future<? extends IdentifiantExemplaire>> ajouteExemplaire(CommandBus bus, AjouteExemplaireBibliothèqueParDéfautSaga saga) {
        return t -> t.apply((id, lecteur) -> bus.send(new AjoutExemplaireCommande(id, saga.isbn, lecteur)));
    }

    private final RegistreLecteurs registreLecteurs;
}
