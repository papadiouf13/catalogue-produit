import entity.Account;
import exception.*;
import valueobject.Money;
import valueobject.PhoneNumber;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MainWithMenu {

    // Base de données en mémoire
    private static final List<Account> comptes = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║     DAKAR-TECH WALLET  v1.0           ║");
        System.out.println("║     Portefeuille Numerique Senegalais  ║");
        System.out.println("╚═══════════════════════════════════════╝");

        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            String choix = scanner.nextLine().trim();
            switch (choix) {
                case "1" -> creerCompte();
                case "2" -> listerComptes();
                case "3" -> crediterCompte();
                case "4" -> debiterCompte();
                case "5" -> effectuerTransfert();
                case "6" -> voirSolde();
                case "0" -> {
                    System.out.println("\nAu revoir !");
                    continuer = false;
                }
                default  -> System.out.println("  Choix invalide. Reessayez.\n");
            }
        }
        scanner.close();
    }

    // ── MENU ────────────────────────────────────────────────────────────────
    private static void afficherMenu() {
        System.out.println("┌───────────────────────────────────────┐");
        System.out.println("│              MENU PRINCIPAL           │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│  1. Creer un compte                   │");
        System.out.println("│  2. Lister tous les comptes           │");
        System.out.println("│  3. Crediter un compte                │");
        System.out.println("│  4. Debiter un compte                 │");
        System.out.println("│  5. Effectuer un transfert            │");
        System.out.println("│  6. Voir le solde d'un compte         │");
        System.out.println("│  0. Quitter                           │");
        System.out.println("└───────────────────────────────────────┘");
        System.out.print("Votre choix : ");
    }

    // ── 1. CRÉER UN COMPTE ───────────────────────────────────────────────────
    private static void creerCompte() {
        System.out.println("\n--- Creation d'un compte ---");
        try {
            System.out.print("Nom du proprietaire : ");
            String nom = scanner.nextLine().trim();

            System.out.print("Numero de telephone (+221XXXXXXXXX ou 7XXXXXXXX) : ");
            String tel = scanner.nextLine().trim();

            System.out.print("Solde initial (XOF) : ");
            String montantStr = scanner.nextLine().trim();

            PhoneNumber phone   = new PhoneNumber(tel);
            Money       solde   = new Money(new BigDecimal(montantStr), "XOF");
            Account     compte  = new Account(nom, phone, solde);

            comptes.add(compte);
            System.out.println("  Compte cree avec succes !");
            System.out.println("  ID     : " + compte.getId());
            System.out.println("  Titulaire : " + compte.getOwnerName());
            System.out.println("  Telephone : " + compte.getPhoneNumber());
            System.out.println("  Solde     : " + compte.getBalance());

        } catch (NumberFormatException e) {
            System.out.println("  Erreur : montant invalide (entrez un nombre).");
        } catch (Exception e) {
            System.out.println("  Erreur : " + e.getMessage());
        }
        System.out.println();
    }

    // ── 2. LISTER LES COMPTES ───────────────────────────────────────────────
    private static void listerComptes() {
        System.out.println("\n--- Liste des comptes ---");
        if (comptes.isEmpty()) {
            System.out.println("  Aucun compte enregistre.\n");
            return;
        }
        for (int i = 0; i < comptes.size(); i++) {
            Account c = comptes.get(i);
            System.out.printf("  [%d] %-20s | %-15s | Solde : %s%n",
                    i + 1,
                    c.getOwnerName(),
                    c.getPhoneNumber().toString(),
                    c.getBalance()
            );
        }
        System.out.println();
    }

    // ── 3. CREDITER ──────────────────────────────────────────────────────────
    private static void crediterCompte() {
        System.out.println("\n--- Credit ---");
        Account compte = choisirCompte();
        if (compte == null) return;

        try {
            System.out.print("Montant a crediter (XOF) : ");
            BigDecimal montant = new BigDecimal(scanner.nextLine().trim());
            compte.crediter(new Money(montant, "XOF"));
        } catch (NumberFormatException e) {
            System.out.println("  Erreur : montant invalide.");
        } catch (Exception e) {
            System.out.println("  Erreur : " + e.getMessage());
        }
        System.out.println();
    }

    // ── 4. DEBITER ───────────────────────────────────────────────────────────
    private static void debiterCompte() {
        System.out.println("\n--- Debit ---");
        Account compte = choisirCompte();
        if (compte == null) return;

        try {
            System.out.print("Montant a debiter (XOF) : ");
            BigDecimal montant = new BigDecimal(scanner.nextLine().trim());
            compte.debiter(new Money(montant, "XOF"));
        } catch (NumberFormatException e) {
            System.out.println("  Erreur : montant invalide.");
        } catch (Exception e) {
            System.out.println("  Erreur : " + e.getMessage());
        }
        System.out.println();
    }

    // ── 5. TRANSFERT ─────────────────────────────────────────────────────────
    private static void effectuerTransfert() {
        System.out.println("\n--- Transfert ---");

        if (comptes.size() < 2) {
            System.out.println("  Il faut au moins 2 comptes pour effectuer un transfert.\n");
            return;
        }

        System.out.println("  Compte SOURCE :");
        Account source = choisirCompte();
        if (source == null) return;

        System.out.println("  Compte DESTINATAIRE :");
        Account destinataire = choisirCompte();
        if (destinataire == null) return;

        try {
            System.out.print("Montant a transferer (XOF) : ");
            BigDecimal montant = new BigDecimal(scanner.nextLine().trim());
            source.transfererVers(destinataire, new Money(montant, "XOF"));
        } catch (NumberFormatException e) {
            System.out.println("  Erreur : montant invalide.");
        } catch (Exception e) {
            System.out.println("  Erreur : " + e.getMessage());
        }
        System.out.println();
    }

    // ── 6. VOIR SOLDE ────────────────────────────────────────────────────────
    private static void voirSolde() {
        System.out.println("\n--- Solde ---");
        Account compte = choisirCompte();
        if (compte == null) return;

        System.out.println("  Titulaire : " + compte.getOwnerName());
        System.out.println("  Telephone : " + compte.getPhoneNumber());
        System.out.println("  Solde     : " + compte.getBalance());
        System.out.println();
    }

    // ── HELPER : choisir un compte dans la liste ────────────────────────────
    private static Account choisirCompte() {
        if (comptes.isEmpty()) {
            System.out.println("  Aucun compte disponible.\n");
            return null;
        }
        listerComptes();
        System.out.print("  Choisissez un numero de compte : ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index < 0 || index >= comptes.size()) {
                System.out.println("  Numero invalide.");
                return null;
            }
            return comptes.get(index);
        } catch (NumberFormatException e) {
            System.out.println("  Entree invalide.");
            return null;
        }
    }
}