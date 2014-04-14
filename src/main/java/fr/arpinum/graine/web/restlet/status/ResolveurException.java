package fr.arpinum.graine.web.restlet.status;


import org.restlet.data.Status;
import org.restlet.representation.Representation;

public interface ResolveurException {

    boolean peutRésourdre(Throwable throwable);

    Status status();

    Representation representation(Throwable throwable);
}
