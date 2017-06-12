package authentification.configuration;

import authentification.dao.UtilisateurDao;
import authentification.infrastructure.dao.mongo.UtilisateurDaoJongo;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.jongo.Jongo;


public class ConfigurationGuice extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public UtilisateurDao utilisateurDao(Jongo jongo) {
        return new UtilisateurDaoJongo(jongo);
    }

}
