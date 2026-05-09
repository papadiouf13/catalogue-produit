package valueobject;

import exception.InvalidPhoneNumberException;
import java.util.regex.Pattern;

public record PhoneNumber(String value) {

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+221)?(7[0678])\\d{7}$");

    public PhoneNumber {
        if (value == null || value.isBlank())
            throw new InvalidPhoneNumberException("(null ou vide)");

        value = value.replaceAll("[\\s\\-]", "");

        if (!PHONE_PATTERN.matcher(value).matches())
            throw new InvalidPhoneNumberException(value);
    }

    public String toInternationalFormat() {
        if (value.startsWith("+221")) return value;
        return "+221" + value;
    }

    @Override
    public String toString() {
        return toInternationalFormat();
    }
}