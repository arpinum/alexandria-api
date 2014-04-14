package fr.arpinum.graine.web.restlet.router;

import com.google.inject.Injector;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.resource.Finder;
import org.restlet.resource.ServerResource;

public class GuiceFinder extends Finder {

    public GuiceFinder(final Context context, final Class<? extends ServerResource> target, final Injector injector) {
        super(context, target);
        this.injector = injector;
    }

    @Override
    public ServerResource create(final Request request, final Response response) {
        return injector.getInstance(getTargetClass());
    }

    private final Injector injector;
}
