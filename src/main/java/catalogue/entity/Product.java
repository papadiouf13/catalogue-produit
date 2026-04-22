package catalogue.entity;

import catalogue.exception.InvalidProductException;
import catalogue.valueobject.Money;
import catalogue.valueobject.SKU;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final SKU sku;
    private final String name;
    private Money price; // mutable uniquement via applyDiscount

    /**
     * Constructeur principal.
     * L'ID est généré automatiquement.
     * Le nom et le SKU sont obligatoires.
     */
    public Product(SKU sku, String name, Money price) {
        if (sku == null) {
            throw new InvalidProductException("Le SKU ne peut pas être null.");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidProductException("Le nom du produit ne peut pas être vide.");
        }
        if (price == null) {
            throw new InvalidProductException("Le prix ne peut pas être null.");
        }

        this.id    = UUID.randomUUID();
        this.sku   = sku;
        this.name  = name;
        this.price = price;
    }

    // ── Getters (pas de setters publics) ──────────────────────────────────────

    public UUID getId() {
        return id;
    }

    public SKU getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    // ── Comportement métier ────────────────────────────────────────────────────

    /**
     * Applique une remise en pourcentage sur le prix.
     * Le pourcentage doit être compris entre 0.1 et 100 inclus.
     */
    public void applyDiscount(BigDecimal percentage) {
        if (percentage == null) {
            throw new InvalidProductException("Le pourcentage de remise ne peut pas être null.");
        }
        if (percentage.compareTo(new BigDecimal("0.1")) < 0
                || percentage.compareTo(new BigDecimal("100")) > 0) {
            throw new InvalidProductException(
                "Le pourcentage de remise doit être compris entre 0.1 et 100. Valeur reçue : " + percentage
            );
        }

        // Calcul : nouveauPrix = prix * (1 - pourcentage / 100)
        BigDecimal factor = BigDecimal.ONE
            .subtract(percentage.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP));

        BigDecimal newAmount = this.price.amount()
            .multiply(factor)
            .setScale(2, RoundingMode.HALF_UP);

        // Mise à jour sécurisée via un nouvel objet Money (immuable)
        this.price = new Money(newAmount, this.price.currency());
    }

    @Override
    public String toString() {
        return "Product{"
            + "id=" + id
            + ", sku=" + sku
            + ", name='" + name + '\''
            + ", price=" + price
            + '}';
    }
}