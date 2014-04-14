package fr.arpinum.graine.infrastructure.bus;

@SuppressWarnings("UnusedDeclaration")
public class ErreurBus extends RuntimeException {

    public ErreurBus(Throwable cause) {
        super(cause);
    }

    public ErreurBus(String message, Throwable cause) {

        super(message, cause);
    }

    public ErreurBus(String message) {

        super(message);
    }
}
