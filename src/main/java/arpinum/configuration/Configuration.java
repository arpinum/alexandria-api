package arpinum.configuration;

import com.google.inject.Stage;
import org.slf4j.Logger;

public final class Configuration {

    public static Stage stage(String context, Logger logger) {
        final String env = Environment.get();
        logger.info("Configuring {} in {}", context, env);
        if ("dev".equals(env)) {
            return Stage.DEVELOPMENT;
        }
        return Stage.PRODUCTION;
    }


    private Configuration() {

    }

}
