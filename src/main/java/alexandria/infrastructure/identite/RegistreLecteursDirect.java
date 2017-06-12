package alexandria.infrastructure.identite;


import alexandria.modele.lecteur.FicheLecteur;
import alexandria.modele.lecteur.Lecteur;
import alexandria.modele.lecteur.RegistreLecteurs;
import arpinum.infrastructure.bus.Io;
import authentification.application.UtilisateurService;
import authentification.modele.Utilisateur;
import io.vavr.concurrent.Future;

import javax.inject.Inject;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

public class RegistreLecteursDirect implements RegistreLecteurs {

    @Inject
    public RegistreLecteursDirect(UtilisateurService service, @Io ExecutorService executor) {
        this.service = service;
        this.executor = executor;
    }

    @Override
    public Future<Lecteur> trouve(String id) {
        return trouveEtMap(id, utilisateur -> new Lecteur(utilisateur.getId()));
    }

    @Override
    public Future<FicheLecteur> ficheDe(String id) {
        return trouveEtMap(id, utilisateur -> new FicheLecteur(id, utilisateur.getPrénom(), utilisateur.getNom()));
    }

    private <T> Future<T> trouveEtMap(String id, Function<Utilisateur, T> mapper) {
        return Future.fromTry(executor,
                service.trouve(id)
                        .map(mapper).toTry());
    }

    private UtilisateurService service;
    private ExecutorService executor;
}
