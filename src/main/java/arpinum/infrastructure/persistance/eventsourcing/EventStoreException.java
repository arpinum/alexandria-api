package arpinum.infrastructure.persistance.eventsourcing;


public class EventStoreException extends RuntimeException {

    public EventStoreException(Throwable cause) {
        super(cause);
    }
}
