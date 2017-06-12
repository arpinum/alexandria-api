package authentification.configuration;

import authentification.dao.UtilisateurDao;
import authentification.infrastructure.dao.mongo.UtilisateurDaoJongo;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.cfg4j.provider.ConfigurationProvider;
import org.jongo.Jongo;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.profile.JwtGenerator;
import org.pac4j.jwt.profile.JwtProfile;


public class ConfigurationGuice extends AbstractModule {
    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    public UtilisateurDao utilisateurDao(Jongo jongo) {
        return new UtilisateurDaoJongo(jongo);
    }

    @Provides
    @Singleton
    JwtGenerator<JwtProfile> generator(ConfigurationProvider provider) {
        return new JwtGenerator<>(new SecretSignatureConfiguration(provider.getProperty("jwt.secret", String.class)));
    }
}
