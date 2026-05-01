import java.util.*;
import java.util.stream.*;

public class Niveau3 {

    record Product(String name, double price) {}
    record Transaction(double amount, String currency, String userType) {}
    record Order(List<Item> items) {}
    record Item(String name, double price) {}
    record Student(String name, double grade) {}
    record User(String name, String email, String role, Address address) {}
    record Address(String street) {}

    static List<Product> products = List.of(
            new Product("Laptop", 100000),
            new Product("Souris", 2000),
            new Product("Écran", 75000),
            new Product("Clavier", 5000)
    );

    static List<Transaction> transactions = List.of(
            new Transaction(500, "XOF", "STANDARD"),
            new Transaction(200, "EUR", "PREMIUM"),
            new Transaction(-100, "XOF", "STANDARD"),
            new Transaction(750, "XOF", "PREMIUM"),
            new Transaction(300, "EUR", "STANDARD")
    );

    static List<Order> orders = List.of(
            new Order(List.of(new Item("Stylo", 100), new Item("Cahier", 250))),
            new Order(List.of(new Item("Règle", 200))),
            new Order(List.of(new Item("Gomme", 50), new Item("Crayon", 100)))
    );

    static List<Student> students = List.of(
            new Student("Fatou", 14.0),
            new Student("Ali", 8.5),
            new Student("Choubidou", 10.0),
            new Student("Diana", 6.0),
            new Student("Eve", 17.5)
    );

    public static void main(String[] args) {
        exercice11();
        exercice12();
        exercice13();
        exercice14();
        exercice15();
    }

    // Exercice 11
    static void exercice11() {
        Map<String, List<Transaction>> byCurrency = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::currency));

        byCurrency.forEach((currency, txs) ->
                System.out.println(currency + " : " + txs.size() + " transaction(s)")


        );
    }

    // Exercice 12
    static void exercice12() {
        DoubleSummaryStatistics stats = products.stream()
                .collect(Collectors.summarizingDouble(Product::price));

        System.out.println("Max     : " + stats.getMax());
        System.out.println("Min     : " + stats.getMin());
        System.out.printf("Moyenne : %.2f%n", stats.getAverage());
    }

    // Exercice 13
    static void exercice13() {
        Map<Boolean, List<Student>> results = students.stream()
                .collect(Collectors.partitioningBy(s -> s.grade() >= 10));

        System.out.println("Admis  : " + results.get(true).stream().map(Student::name).toList());
        System.out.println("Recalé : " + results.get(false).stream().map(Student::name).toList());
    }

    // Exercice 14 :
    static void exercice14() {
        List<Item> allItems = orders.stream()
                .flatMap(order -> order.items().stream())
                .collect(Collectors.toList());

        allItems.forEach(i -> System.out.println("  - " + i.name() + " : " + i.price() + " CFA"));
    }

    // Exercice 15 :
    static void exercice15() {
        User userWithNull = new User("Test", "test@mail.com", "USER", null);

        String street = Optional.ofNullable(userWithNull)
                .map(User::address)
                .map(Address::street)
                .orElse("Rue inconnue");

        System.out.println("Rue : " + street);
    }
}