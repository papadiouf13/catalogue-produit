import java.util.*;
import java.util.stream.*;

public class Niveau2 {

    record User(String name, String email, String role, Address address) {}
    record Address(String street) {}

    static List<String> fruits = List.of(
            "Ananas", "Avocat", "Abricot", "Banane", "Mangue"
    );

    static List<User> users = List.of(
            new User("Mamadou Diop", "mamadou.diop@gmail.com", "ADMIN", new Address("Rue 10, Dakar")),
            new User("Fatou Ndiaye", "fatou.ndiaye@gmail.com", "USER", new Address("Parcelles Assainies, Dakar")),
            new User("Ibrahima Fall", "ibrahima.fall@gmail.com", "USER", null),
            new User("Awa Sarr", "awa.sarr@gmail.com", "ADMIN", new Address("Mermoz, Dakar")),
            new User("Cheikh Gueye", "cheikh.gueye@gmail.com", "USER", new Address("Guédiawaye, Dakar"))
    );

    static List<Integer> prices = List.of(50, 120, 80, 200, 150, 90, 300);

    static List<String> names = List.of(
            "Mamadou", "Fatou", "Awa", "Ibrahima", "Fatou", "Mamadou", "Cheikh"
    );

    public static void main(String[] args) {
        exercice6();
        exercice7();
        exercice8();
        exercice9();
        exercice10();
    }

    // Exercice 6
    static void exercice6() {
        List<String> result = fruits.stream()
                .filter(fruit -> fruit.startsWith("A"))
                .toList();

        System.out.println(result);
    }

    // Exercice 7
    static void exercice7() {
        List<String> emails = users.stream()
                .map(User::email)
                .toList();

        System.out.println(emails);
    }

    // Exercice 8
    static void exercice8() {
        int total = prices.stream()
                .filter(price -> price > 100)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Total : " + total);
    }

    // Exercice 9
    static void exercice9() {
        List<String> uniqueNames = names.stream()
                .distinct()
                .sorted()
                .toList();

        System.out.println(uniqueNames);
    }

    // Exercice 10
    static void exercice10() {
        Optional<User> admin = users.stream()
                .filter(user -> user.role().equals("ADMIN"))
                .findAny();

        admin.ifPresent(user -> System.out.println("Admin : " + user.name()));
    }
}