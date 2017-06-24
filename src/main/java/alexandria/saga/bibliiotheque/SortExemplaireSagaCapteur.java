package alexandria.saga.bibliiotheque;

import alexandria.command.bibliotheque.SortExemplaireCommande;
import alexandria.modele.bibliotheque.Emprunt;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.command.CommandBus;
import arpinum.saga.SagaHandler;
import io.vavr.concurrent.Future;

import javax.inject.Inject;

public class SortExemplaireSagaCapteur implements SagaHandler<Emprunt, SortExemplaireSaga> {

    @Inject
    public SortExemplaireSagaCapteur(RegistreLecteurs registreLecteurs) {
        this.registreLecteurs = registreLecteurs;
    }

    @Override
    public Future<Emprunt> run(CommandBus bus, SortExemplaireSaga saga) {
        return registreLecteurs.trouve(saga.idLecteur)
                .map(l -> new SortExemplaireCommande(saga.idBibliotheque, l, saga.idExemplaire))
                .flatMap(bus::send);
    }

    private RegistreLecteurs registreLecteurs;
}
