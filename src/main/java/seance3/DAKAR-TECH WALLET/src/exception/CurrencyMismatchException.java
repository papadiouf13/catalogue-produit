package exception;

public class CurrencyMismatchException extends RuntimeException {
    public CurrencyMismatchException(String devise1, String devise2) {
        super("Impossible d'operer entre deux devises differentes : "
                + devise1 + " et " + devise2);
    }
}