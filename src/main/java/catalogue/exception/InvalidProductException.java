package catalogue.exception;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String msg) {
        super(msg);
    }
}