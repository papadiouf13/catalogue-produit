package catalogue.exception;

public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String currency) {
        super("Devise invalide : '" + currency + "'. Seules 'FCFA' et 'EUR' sont acceptées.");
    }
}