package arpinum.ddd;

public class NotAllowedException extends BusinessError {

    public NotAllowedException() {
        super("NOT_ALLOWED");
    }
}
