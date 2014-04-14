package alexandria.infrastructure.persistance.memoire;

import org.junit.rules.ExternalResource;
import alexandria.modele.LocalisateurEntrepots;

public class AvecEntrepotsMemoire extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        LocalisateurEntrepots.initialise(new LocalisateurEntrepotsMemoire());
    }

    @Override
    protected void after() {
        LocalisateurEntrepots.initialise(null);
    }
}
