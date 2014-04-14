package fr.arpinum.graine.web.restlet;

import fr.arpinum.graine.web.restlet.filtre.FiltreCors;
import fr.arpinum.graine.web.restlet.router.GuiceRouter;
import fr.arpinum.graine.web.restlet.status.ApplicationStatusService;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Handler;
import java.util.logging.LogManager;

@SuppressWarnings("UnusedDeclaration")
public abstract class BaseApplication extends Application {

    public BaseApplication(Context context) {
        super(context);
        // Désactivation des log JUL
        final java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
        final Handler[] handlers = rootLogger.getHandlers();
        for (final Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }
        setStatusService(new ApplicationStatusService());
        SLF4JBridgeHandler.install();
    }

    @Override
    public synchronized void start() throws Exception {
        LOGGER.info("Démarrage de l'application");
        super.start();
    }

    @Override
    public synchronized void stop() throws Exception {
        LOGGER.info("Arrêt de l'application");
    }

    @Override
    public Restlet createInboundRoot() {
        final FiltreCors filtre = new FiltreCors(getContext());
        filtre.setNext(routes());
        return filtre;
    }

    protected abstract GuiceRouter routes();

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseApplication.class);
}
