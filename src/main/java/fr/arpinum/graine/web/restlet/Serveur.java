package fr.arpinum.graine.web.restlet;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("UnusedDeclaration")
public class Serveur {

    public Serveur(BaseApplication application) {
        this.application = application;
    }

    public void start(int port) throws Exception {
        LOGGER.info("Démarrage du serveur sur le port {}", port);
        final Component component = new Component();
        component.getServers().add(Protocol.HTTP, port);
        component.getDefaultHost().attach(application);
        component.start();
    }

    private final BaseApplication application;
    private static final Logger LOGGER = LoggerFactory.getLogger(Serveur.class);
}
