package com.myorg.optionals;

import java.util.Optional;

/**
 * OptionalBasics class consists of fundamental use of optionals in java.
 */
public class OptionalBasics {
    /**
     * -------------------------------------
     * 1. Optional.empty();
     * -------------------------------------
     *      - Initializes an empty optional
     *
     *
     * -------------------------------------
     * 2. Optional.of(T value);
     * -------------------------------------
     *      - Used where for sure we know that the value passed is not null.Because we may get a NullPointerException
     *        if a null value is passed. Have a look at following example:
     *
     *      Code:
     *          Optional<String> optionalValue = Optional.of(null);
     *
     *          System.out.println(optionalValue.isPresent());
     *          System.out.println(optionalValue.isEmpty());
     *
     *      Output:
     *          Exception in thread "main" java.lang.NullPointerException at java.base/java.util.Objects.requireNonNull(Objects.java:14)
     *
     *
     * -------------------------------------
     * 3. Optional.ofNullable(T value);
     * -------------------------------------
     *      - Used when it's not sure whether the value is present or null. Here, it will not throw a NullPointerException
     *        even if the value is null
     */

    public static void emptyOptionalUsage() {
        // Declaring an optional of type Object and instantiating it as an empty optional
        Optional<Object> empty = Optional.empty();

        System.out.println(empty); // Optional.empty

        // Returns true if there is a value inside the optional. So in this case, it's false
        System.out.println(empty.isPresent()); // false

        // Returns true if there is no value inside the optional/ if the optional is empty (inverse of .isPresent())
        System.out.println(empty.isEmpty()); // true
    }

    public static void optionalOfUsage() {
        Optional<String> stringOptional = Optional.of("hello");

        System.out.println(stringOptional.isPresent()); // true
        System.out.println(stringOptional.isEmpty()); // false
    }

    public static void optionalOfNullPointerExceptionScenario() {
        Optional<String> stringOptional = Optional.of(null);

        // This will throw a NullPointerException as .of() should only be used if the value is not null for sure
        System.out.println(stringOptional.isPresent());
        System.out.println(stringOptional.isEmpty());
    }

    public static void optionalOfNullableScenario() {
        // ofNullable will not cause any NullPointerException even if the value is null
        Optional<String> stringOptional = Optional.ofNullable(null);

        System.out.println(stringOptional.isPresent()); // false
        System.out.println(stringOptional.isEmpty()); // true
    }

    public static void orElseUsageScenario1() {
        Optional<String> text = Optional.of("hello");

        System.out.println(text.isPresent()); // true
        System.out.println(text.isEmpty()); // false
        System.out.println(text); // Optional[hello]
        System.out.println(text.get()); // hello

        String textValue = text.orElse("world");

        System.out.println(textValue); // hello

        // making the text empty again by assigning an empty optional
        text = Optional.empty();

        // We can specify a default value for the optional with orElse, if the value inside the optional is not present
        textValue = text.orElse("world");

        System.out.println(textValue); // world
    }

    public static void orElseUsageScenario2() {
        Optional<String> stringOptional = Optional.ofNullable("hello");
        String textFromStringOptional = stringOptional
                // converts the String value to upper case, if it is available inside the Optional (in stringOptional)
                .map(String::toUpperCase)
                // If the value inside the optional is not available, this default value will be assigned
                .orElse("world");

        System.out.println(textFromStringOptional);// HELLO

        Optional<String> optional2 = Optional.ofNullable(null);
        String secondOptionalStringValue = optional2.map(String::toUpperCase).orElse("default value");

        System.out.println(secondOptionalStringValue); // default value
    }

    public static void orElseGetUsageScenario() {
        Optional<String> optional = Optional.ofNullable(null);

        String optionalValue = optional
                .map(String::toLowerCase)
                /* with orElseGet , we can pass a supplier using a lambda expression containing any logic to get a
                    default value from it */
                .orElseGet(() -> {
                    // Here we can do any extra computation required to retrieve an alternative value
                    return "w or l d".toUpperCase().replace(" ", "");
                });

        System.out.println(optionalValue); // WORLD
    }

    public static void orUsageScenario() {
        Optional<Integer> integerOptional = Optional.ofNullable(null);

        Integer value = integerOptional
                .map(i -> i * 2)
                // With or we can pass a supplier and return a value of type Optional
                .or(() -> {
                    // We can include additional computation here when constructing the Optioanl if required
                    return Optional.of(Integer.valueOf(23));
                })
                .map(i -> i * 10)
                .get();

        System.out.println(value); // 230
    }

    public static void orElseThrowDefaultExceptionScenario() {
        Optional<String> optional = Optional.ofNullable(null);

        String value = optional
                .map(String::toUpperCase)
                // no-args orElseThrow will throw java.util.NoSuchElementException if there's no value present inside optional
                .orElseThrow();
    }

    public static void orElseThrowCustomExceptionScenario() {
        Optional<String> optional = Optional.ofNullable(null);

        String value = optional
                .map(String::toUpperCase)
                // We can manually specify an exception to be thrown if the value inside the optional is not present
                .orElseThrow(IllegalStateException::new);
    }

    public static void ifPresentUsageScenario() {
        Optional<String> optional = Optional.ofNullable(null);

        // Carefully note that this method is not isPresent but ifPresent

        // ifPresent will take the actual value inside the optional if it is present/ only if it is present
        optional.ifPresent(valueInside -> {
            // Here this code will not be executed as there's no value inside the optional
            System.out.println(valueInside);
        });

        // Putting a value inside the optional
        optional = Optional.of("Test Value");

        optional.ifPresent(insideValue -> {
            // This will be printed to console as the value inside the optional is present
            System.out.println("Value is available: " + insideValue); // Value is available: Test Value
        });
    }

    public static void ifPresentOrElseUsageScenario() {
        Optional<String> optional = Optional.ofNullable("Hello");

        /** what ifPresentorElse does is that, if the value inside the optional is available it will be evaluated
         * and execute first Consumer (lambda expressen passed). Otherwise it'll execute 2nd lambda expression
         * which is a Runnable passed as a parameter */

        // Here the output will be "HELLO" as the value is present
        optional.ifPresentOrElse(
                valueInside -> System.out.println(valueInside.toUpperCase()),
                () -> System.out.println("world")
        );

        // Assigning an optional with a null value inside
        optional = Optional.ofNullable(null);

        // Here the output will be "world" as the value inside is null and not present
        optional.ifPresentOrElse(
                valueInside -> System.out.println(valueInside.toUpperCase()),
                () -> System.out.println("world")
        );
    }

    public static void main(String[] args) {
        System.out.println("emptyOptionalUsage()\n");
        emptyOptionalUsage();

        System.out.println("\noptionalOfUsage()\n");
        optionalOfUsage();

        System.out.println("\norElseUsageScenario1\n");
        orElseUsageScenario1();

        // optionalOfNullPointerExceptionScenario(); // Throws a NullPointerException

        System.out.println("\noptionalOfNullableScenario\n");
        optionalOfNullableScenario();

        System.out.println("\norElseUsageScenario2\n");
        orElseUsageScenario2();

        System.out.println("\norElseGetUsageScenario\n");
        orElseGetUsageScenario();

        System.out.println("\norUsageScenario\n");
        orUsageScenario();

        // System.out.println("\orElseThrow_DefaultExceptionScenario\n");
        // orElseThrowDefaultExceptionScenario(); // will throw NoSuchElementException

        // System.out.println("\orElseThrow_CustomExceptionScenario\n");
        // orElseThrowCustomExceptionScenario(); // will throw specified IllegalStateException

        System.out.println("\nifPresentUsageScenario\n");
        ifPresentUsageScenario();

        System.out.println("\nifPresentOrElseUsageScenario\n");
        ifPresentOrElseUsageScenario();
    }
}
