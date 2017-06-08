package alexandria.modele.lecteur;


import io.vavr.concurrent.Future;

public interface RegistreLecteurs {

    Future<Lecteur> trouve(String id);

    Future<FicheLecteur> ficheDe(String id);
}
