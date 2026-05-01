import java.util.function.*;

public class Niveau1 {

    @FunctionalInterface
    interface Validator {
        boolean validate(String s);
    }

    public static void main(String[] args) {
        exercice1();
        exercice2();
        exercice3();
        exercice4();
        exercice5();
    }

    // Exercice 1
    static void exercice1() {
        Predicate<Integer> isPositive = n -> n > 0;

        System.out.println(isPositive.test(5));
        System.out.println(isPositive.test(-3));
        System.out.println(isPositive.test(0));
    }

    // Exercice 2
    static void exercice2() {
        Consumer<String> logger = s -> System.out.println("[LOG] " + s.toUpperCase());

        logger.accept("connexion réussie");
        logger.accept("erreur détectée");
    }

    // Exercice 3
    static void exercice3() {
        Function<String, Integer> doubleValue = s -> Integer.parseInt(s) * 2;

        System.out.println(doubleValue.apply("10"));
        System.out.println(doubleValue.apply("7"));
    }

    // Exercice 4
    static void exercice4() {
        Supplier<Double> interestRateSupplier = () -> Math.random() * 5.0;

        System.out.printf("Taux : %.4f%n", interestRateSupplier.get());
        System.out.printf("Taux : %.4f%n", interestRateSupplier.get());
    }

    // Exercice 5
    static void exercice5() {
        Validator emailValidator = s -> s != null && s.contains("@");

        System.out.println(emailValidator.validate("test@gmail.com"));
        System.out.println(emailValidator.validate("testgmail.com"));
        System.out.println(emailValidator.validate(null));
    }
}