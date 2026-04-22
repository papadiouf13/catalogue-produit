package catalogue.exception;

public class InvalidSkuException extends RuntimeException {
    public InvalidSkuException(String sku) {
        super("SKU invalide : '" + sku + "'. Format attendu : 3 lettres majuscules, un tiret, 4 à 6 chiffres. Ex: TEC-10204");
    }
}