package catalogue.valueobject;

import catalogue.exception.InvalidCurrencyException;
import catalogue.exception.InvalidMoneyOperationException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public record Money(BigDecimal amount, String currency) {
    private static final Set<String> VALID_CURRENCIES = Set.of("FCFA", "EUR");
    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Le montant ne peut pas être null.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "Le montant ne peut pas être négatif : " + amount
            );
        }
        if (currency == null || !VALID_CURRENCIES.contains(currency)) {
            throw new InvalidCurrencyException(currency);
        }
    }
    public Money add(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Impossible d'additionner avec un Money null.");
        }
        if (!this.currency.equals(other.currency())) {
            throw new InvalidMoneyOperationException(
                "Impossible d'additionner des montants de devises différentes : "
                + this.currency + " et " + other.currency()
            );
        }
        return new Money(this.amount.add(other.amount()), this.currency);
    }

    @Override
    public String toString() {
        return amount.setScale(2, RoundingMode.HALF_UP) + " " + currency;
    }
}