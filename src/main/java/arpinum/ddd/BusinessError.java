package arpinum.ddd;

@SuppressWarnings("UnusedDeclaration")
public class BusinessError extends RuntimeException {

    public BusinessError(String code) {
        super(code);
    }
}
