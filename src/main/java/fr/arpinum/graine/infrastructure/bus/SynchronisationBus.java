package fr.arpinum.graine.infrastructure.bus;


public interface SynchronisationBus {

    default void avantExecution(Message<?> message) {

    }

    default void surErreur() {

    }

    default void apresExecution() {

    }

    default void finalement() {

    }
}
