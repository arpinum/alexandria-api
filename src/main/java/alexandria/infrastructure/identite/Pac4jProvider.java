package alexandria.infrastructure.identite;

import org.pac4j.core.config.Config;
import org.pac4j.jax.rs.features.JaxRsConfigProvider;
import org.pac4j.jax.rs.features.Pac4JSecurityFeature;
import org.pac4j.jax.rs.servlet.features.ServletJaxRsContextFactoryProvider;

import javax.inject.Inject;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

@Provider
public class Pac4jProvider implements Feature {

    @Inject
    public Pac4jProvider(Config config) {
        this.config = config;
    }

    @Override
    public boolean configure(FeatureContext context) {
        context
                .register(new JaxRsConfigProvider(config))
                .register(new ServletJaxRsContextFactoryProvider())
                .register(new Pac4JSecurityFeature());
        return true;
    }

    private Config config;
}
