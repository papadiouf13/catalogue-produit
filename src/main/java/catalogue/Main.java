package catalogue;

import catalogue.entity.Product;
import catalogue.valueobject.Money;
import catalogue.valueobject.SKU;

import java.math.BigDecimal;

public class Main {

    @FunctionalInterface
    interface Action {
        void execute() throws Exception;
    }

    public static void main(String[] args) {
        SKU sku = new SKU("TEC-10204");
        Money prix = new Money(new BigDecimal("25000"), "FCFA");
        Product produit = new Product(sku, "Casque Audio Pro", prix);

        System.out.println(produit);

        produit.applyDiscount(new BigDecimal("20"));
        System.out.println(produit.getPrice());

        Money m1 = new Money(new BigDecimal("10"), "EUR");
        Money m2 = new Money(new BigDecimal("5"), "EUR");
        System.out.println(m1.add(m2));

        run(() -> new Money(new BigDecimal("-500"), "FCFA"));
        run(() -> new Money(new BigDecimal("100"), "USD"));
        run(() -> {
            Money fcfa = new Money(new BigDecimal("5000"), "FCFA");
            Money eur = new Money(new BigDecimal("10"), "EUR");
            fcfa.add(eur);
        });
        run(() -> new SKU("abc-123"));
        run(() -> new SKU(null));
        run(() -> new Product(new SKU("CAT-5001"), "   ", new Money(new BigDecimal("1000"), "FCFA")));
        run(() -> new Product(null, "Produit test", new Money(new BigDecimal("500"), "EUR")));
        run(() -> produit.applyDiscount(new BigDecimal("150")));
        run(() -> produit.applyDiscount(new BigDecimal("0.0")));
    }

    private static void run(Action action) {
        try {
            action.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}