package exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String numero) {
        super("Numero de telephone invalide : '" + numero
                + "'. Format attendu : +221XXXXXXXXX ou 7XXXXXXXX (77, 78, 70, 76)");
    }
}