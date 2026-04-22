package catalogue.valueobject;

import catalogue.exception.InvalidSkuException;
import java.util.regex.Pattern;

public record SKU(String value) {

    // Regex : 3 lettres majuscules, un tiret, 4 à 6 chiffres
    private static final Pattern SKU_PATTERN = Pattern.compile("^[A-Z]{3}-\\d{4,6}$");

    // Constructeur compact
    public SKU {
        if (value == null || value.isBlank()) {
            throw new InvalidSkuException("(null ou vide)");
        }
        if (!SKU_PATTERN.matcher(value).matches()) {
            throw new InvalidSkuException(value);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}