import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Niveau4 {

    record User(String name, String email, String role, Address address) {}
    record Address(String street) {}
    record Transaction(double amount, String currency, String userType) {}
    record Employee(String name, int age, double salary) {}

    static List<User> users = List.of(
            new User("Mamadou Diop", "mamadou.diop@gmail.com", "ADMIN", new Address("Rue 10, Dakar")),
            new User("Fatou Ndiaye", "fatou.ndiaye@gmail.com", "USER", new Address("Parcelles Assainies, Dakar")),
            new User("Ibrahima Fall", "ibrahima.fall@gmail.com", "USER", null),
            new User("Awa Sarr", "awa.sarr@gmail.com", "ADMIN", new Address("Mermoz, Dakar"))
    );

    static List<Transaction> transactions = List.of(
            new Transaction(500, "XOF", "STANDARD"),
            new Transaction(200, "EUR", "PREMIUM"),
            new Transaction(-100, "XOF", "STANDARD"),
            new Transaction(750, "XOF", "PREMIUM"),
            new Transaction(300, "EUR", "STANDARD")
    );

    static List<String> csvLines = List.of(
            "Mamadou;28;350000.0",
            "Fatou;INVALIDE;280000.0",
            "Ibrahima;35;420000.0",
            ";;",
            "Awa;30;390000.0"
    );

    static List<String> ids = List.of("1", "2", "99", "3");

    static Optional<User> findById(String id) {
        Map<String, User> db = Map.of(
                "1", users.get(0),
                "2", users.get(1),
                "3", users.get(3)
        );

        return Optional.ofNullable(db.get(id));
    }

    public static void main(String[] args) {
        exercice16();
        exercice17();
        exercice18();
    }

    // Exercice 16
    static void exercice16() {
        Predicate<Transaction> isXof = t -> t.amount() > 0 && t.currency().equals("XOF");
        Predicate<Transaction> isPremium = t -> t.userType().equals("PREMIUM");
        Predicate<Transaction> complexValidator = isXof.or(isPremium);

        transactions.stream()
                .filter(complexValidator)
                .forEach(t -> System.out.println(
                        "  " + t.currency() + " | " + t.amount() + " | " + t.userType()
                ));
    }

    // Exercice 17
    static void exercice17() {
        List<User> existingUsers = ids.stream()
                .map(Niveau4::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        existingUsers.forEach(u -> System.out.println("  Trouvé : " + u.name()));
    }

    // Exercice 18
    static void exercice18() {
        List<Employee> employees = csvLines.stream()
                .map(line -> {
                    try {
                        String[] parts = line.split(";");
                        String name = parts[0];
                        int age = Integer.parseInt(parts[1]);
                        double salary = Double.parseDouble(parts[2]);

                        return Optional.of(new Employee(name, age, salary));
                    } catch (Exception e) {
                        return Optional.<Employee>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        employees.forEach(e ->
                System.out.println("  " + e.name() + " | " + e.age() + " ans | " + e.salary() + " CFA")
        );
    }
}