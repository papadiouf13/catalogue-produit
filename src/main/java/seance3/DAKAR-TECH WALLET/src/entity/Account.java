package entity;

import exception.InsufficientFundsException;
import exception.InvalidAccountException;
import valueobject.Money;
import valueobject.PhoneNumber;

import java.util.UUID;

public class Account {

    private final UUID id;
    private final String ownerName;
    private final PhoneNumber phoneNumber;
    private Money balance;

    public Account(String ownerName, PhoneNumber phoneNumber, Money initialBalance) {
        if (ownerName == null || ownerName.isBlank())
            throw new InvalidAccountException("Le nom du proprietaire ne peut pas etre vide.");

        if (phoneNumber == null)
            throw new InvalidAccountException("Le numero de telephone est obligatoire.");

        if (initialBalance == null)
            throw new InvalidAccountException("Le solde initial est obligatoire.");

        this.id = UUID.randomUUID();
        this.ownerName = ownerName.trim();
        this.phoneNumber = phoneNumber;
        this.balance = initialBalance;
    }

    public UUID getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public Money getBalance() {
        return balance;
    }

    public void crediter(Money montant) {
        if (montant == null)
            throw new InvalidAccountException("Le montant a crediter ne peut pas etre null.");

        this.balance = this.balance.add(montant);

        System.out.println("[CREDIT] " + ownerName + " : +" + montant
                + " → solde = " + this.balance);
    }

    public void debiter(Money montant) {
        if (montant == null)
            throw new InvalidAccountException("Le montant a debiter ne peut pas etre null.");

        if (!this.balance.isGreaterThanOrEqualTo(montant))
            throw new InsufficientFundsException(
                    this.balance.toString(), montant.toString());

        this.balance = this.balance.subtract(montant);

        System.out.println("[DEBIT]  " + ownerName + " : -" + montant
                + " → solde = " + this.balance);
    }

    public void transfererVers(Account destinataire, Money montant) {
        if (destinataire == null)
            throw new InvalidAccountException("Le compte destinataire ne peut pas etre null.");

        if (destinataire.getId().equals(this.id))
            throw new InvalidAccountException("Impossible de se transferer a soi-meme.");

        if (montant == null)
            throw new InvalidAccountException("Le montant du transfert ne peut pas etre null.");

        System.out.println("\n[TRANSFERT] " + ownerName
                + " → " + destinataire.getOwnerName()
                + " : " + montant);

        this.debiter(montant);
        destinataire.crediter(montant);

        System.out.println("[TRANSFERT OK] "
                + ownerName + " solde restant : " + this.balance
                + " | " + destinataire.getOwnerName()
                + " solde : " + destinataire.getBalance());
    }

    @Override
    public String toString() {
        return "Account{id=" + id
                + ", owner='" + ownerName + '\''
                + ", phone=" + phoneNumber
                + ", balance=" + balance + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account a)) return false;
        return this.id.equals(a.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}