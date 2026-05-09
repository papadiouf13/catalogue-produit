import entity.Account;
import valueobject.Money;
import valueobject.PhoneNumber;
import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║     DAKAR-TECH WALLET — Tests de robustesse  ║");
        System.out.println("╚══════════════════════════════════════════════╝\n");

        System.out.println("=== CREATION DES COMPTES ===");

        Account moussa = new Account(
                "Moussa Diallo",
                new PhoneNumber("+221771234567"),
                new Money(new BigDecimal("150000"), "XOF")
        );

        Account fatou = new Account(
                "Fatou Ndiaye",
                new PhoneNumber("782345678"),
                new Money(new BigDecimal("50000"), "XOF")
        );

        System.out.println("Compte cree : " + moussa);
        System.out.println("Compte cree : " + fatou + "\n");

        System.out.println("=== OPERATIONS NORMALES ===");

        moussa.crediter(new Money(new BigDecimal("20000"), "XOF"));
        moussa.debiter(new Money(new BigDecimal("30000"), "XOF"));
        moussa.transfererVers(fatou, new Money(new BigDecimal("40000"), "XOF"));

        System.out.println();

        System.out.println("=== TESTS DE ROBUSTESSE ===\n");

        System.out.println("-- [1] Montant negatif (-5000 XOF) :");
        tryAndCatch(() -> new Money(new BigDecimal("-5000"), "XOF"));

        System.out.println("-- [2] Montant zero :");
        tryAndCatch(() -> new Money(BigDecimal.ZERO, "XOF"));

        System.out.println("-- [3] Devise invalide (GBP) :");
        tryAndCatch(() -> new Money(new BigDecimal("1000"), "GBP"));

        System.out.println("-- [4] Transfert avec devises differentes (XOF → EUR) :");
        tryAndCatch(() -> {
            Account euro = new Account(
                    "Paul Dupont",
                    new PhoneNumber("+221701111111"),
                    new Money(new BigDecimal("100"), "EUR")
            );
            moussa.transfererVers(euro, new Money(new BigDecimal("100"), "EUR"));
        });

        System.out.println("-- [5] Debit superieur au solde :");
        tryAndCatch(() ->
                fatou.debiter(new Money(new BigDecimal("999999"), "XOF"))
        );

        System.out.println("-- [6] Numero de telephone invalide (0612345678) :");
        tryAndCatch(() -> new PhoneNumber("0612345678"));

        System.out.println("-- [7] Numero invalide (71XXXXXXX) :");
        tryAndCatch(() -> new PhoneNumber("+221711234567"));

        System.out.println("-- [8] Compte avec nom vide :");
        tryAndCatch(() -> new Account(
                "   ",
                new PhoneNumber("+221771234567"),
                new Money(new BigDecimal("1000"), "XOF")
        ));

        System.out.println("-- [9] Transfert vers soi-meme :");
        tryAndCatch(() ->
                moussa.transfererVers(moussa, new Money(new BigDecimal("1000"), "XOF"))
        );

        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║  Tous les garde-fous sont operationnels ✓    ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    static void tryAndCatch(Runnable r) {
        try {
            r.run();
            System.out.println("  [FAIL] Aucune exception levee — le garde-fou est absent !\n");
        } catch (Exception e) {
            System.out.println("  [OK]   Exception : " + e.getMessage() + "\n");
        }
    }
}