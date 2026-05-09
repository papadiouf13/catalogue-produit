package valueobject;

import exception.CurrencyMismatchException;
import exception.InvalidMoneyException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

public record Money(BigDecimal amount, String currency) {

    private static final Set<String> VALID_CURRENCIES = Set.of("XOF", "EUR", "USD");

    public Money {
        if (amount == null)
            throw new InvalidMoneyException("Le montant ne peut pas etre null.");

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidMoneyException(
                    "Le montant doit etre strictement positif. Recu : " + amount);

        if (currency == null || !VALID_CURRENCIES.contains(currency))
            throw new InvalidMoneyException(
                    "Devise invalide : '" + currency + "'. Acceptees : " + VALID_CURRENCIES);
    }

    public Money add(Money other) {
        if (other == null)
            throw new InvalidMoneyException("Impossible d'additionner avec un Money null.");

        if (!this.currency.equals(other.currency()))
            throw new CurrencyMismatchException(this.currency, other.currency());

        return new Money(this.amount.add(other.amount()), this.currency);
    }

    public Money subtract(Money other) {
        if (other == null)
            throw new InvalidMoneyException("Impossible de soustraire un Money null.");

        if (!this.currency.equals(other.currency()))
            throw new CurrencyMismatchException(this.currency, other.currency());

        BigDecimal result = this.amount.subtract(other.amount());

        if (result.compareTo(BigDecimal.ZERO) < 0)
            throw new InvalidMoneyException("Soustraction produirait un montant negatif.");

        return new Money(result, this.currency);
    }

    public boolean isGreaterThanOrEqualTo(Money other) {
        if (!this.currency.equals(other.currency()))
            throw new CurrencyMismatchException(this.currency, other.currency());

        return this.amount.compareTo(other.amount()) >= 0;
    }

    @Override
    public String toString() {
        return amount.setScale(0, RoundingMode.HALF_UP) + " " + currency;
    }
}