package arpinum.configuration;

import arpinum.infrastructure.security.JwtAuth0;
import arpinum.infrastructure.security.JwtBuilder;
import arpinum.infrastructure.security.JwtConfiguration;
import arpinum.infrastructure.security.JwtVerifier;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.cfg4j.provider.ConfigurationProvider;

import javax.inject.Singleton;


public class JwtModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(JwtAuth0.class).in(Singleton.class);
        bind(JwtBuilder.class).to(JwtAuth0.class);
        bind(JwtVerifier.class).to(JwtAuth0.class);
    }

    @Provides
    @Singleton
    public JwtConfiguration configuration(ConfigurationProvider provider) {
        return provider.bind("jwt", JwtConfiguration.class);
    }
}
