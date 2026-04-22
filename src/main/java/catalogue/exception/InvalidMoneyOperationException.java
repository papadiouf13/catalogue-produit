package catalogue.exception;

public class InvalidMoneyOperationException extends RuntimeException {
    public InvalidMoneyOperationException(String msg) {
        super(msg);
    }
}