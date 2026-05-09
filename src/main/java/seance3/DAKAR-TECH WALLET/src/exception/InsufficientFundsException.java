package exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String solde, String montant) {
        super("Solde insuffisant : solde actuel = " + solde
                + ", montant demande = " + montant);
    }
}