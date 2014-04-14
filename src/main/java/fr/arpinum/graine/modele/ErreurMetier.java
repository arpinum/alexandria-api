package fr.arpinum.graine.modele;

@SuppressWarnings("UnusedDeclaration")
public class ErreurMetier extends RuntimeException {

    public ErreurMetier(String message) {
        super(message);
    }
}
