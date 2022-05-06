package com.myorg.optionals;

import java.util.Optional;

public class OptionalBasicsDemo {
    /**
     * 1. Optional.empty()
     *      Defines an empty optional
     *
     * 2. Optional.of(T value);
     *      Used where for sure we know that the value passed is not null
     *      Because we may get NullPointerExceptions if a null value is passed
     *
     * Ex:
     *      Optional<String> optionalValue = Optional.of(null);
     *      System.out.println(optionalValue.isPresent());
     *      System.out.println(optionalValue.isEmpty());
     *
     *      Exception in thread "main" java.lang.NullPointerException
     *      at java.base/java.util.Objects.requireNonNull(Objects.java:14)
     *
     * 3. Optional.ofNullable(T value)
     *      Used when it's not sure whether the value is present or null
     */

    public static void emptyOptionalExampleWithMethods() {
        // Declaring an instantiating an optional of type Object
        Optional<Object> empty = Optional.empty();

        System.out.println(empty); // Optional.empty

        // Returns true if there is a value inside the optional
        System.out.println(empty.isPresent()); // false

        // Returns true if there is a no value inside the optional (inverse of .isPresent())
        System.out.println(empty.isEmpty()); // true
    }

    public static void optionalExample2() {
        Optional<String> stringOptional = Optional.of("hello");

        System.out.println(stringOptional.isPresent()); // true

        System.out.println(stringOptional.isEmpty()); // false
    }

    public static void optionalExample3_orElse() {
        Optional<String> hello = Optional.of("hello");

        System.out.println(hello.isPresent()); // true
        System.out.println(hello.isEmpty()); // false
        System.out.println(hello); // Optional[hello]
        System.out.println(hello.get()); // hello

        String textValue = hello.orElse("world");

        System.out.println(textValue); // hello

        // making the hello empry again
        hello = Optional.empty();

        // We can specify a default value for the optional with orElse if the value inside the optional is not present
        textValue = hello.orElse("world");

        System.out.println(textValue); // world
    }


    public static void nullPointerExceptionScenario() {
        Optional<String> stringOptional = Optional.of(null);

        // This will cause to throw a NullPointerException as .of() should only be used if the value is not null for sure
        System.out.println(stringOptional.isPresent());
        System.out.println(stringOptional.isEmpty());
    }

    public static void optionalExample4_ofNullable() {
        // ofNullable will not cause any NullPointerException even if the value is null
        Optional<String> stringOptional = Optional.ofNullable(null);

        System.out.println(stringOptional.isPresent()); // false
        System.out.println(stringOptional.isEmpty()); // true

    }

    public static void optionalExample5() {
        Optional<String> stringOptional = Optional.ofNullable("hello");
        String textFromStringOptional = stringOptional
                // converts the String value to upper case if it is available inside the Optional (in stringOptional)
                .map(String::toUpperCase).orElse("world"); // If the value inside the optional is not available, this
        // default value is assigned

        System.out.println(textFromStringOptional);// HELLO

        Optional<String> optional2 = Optional.ofNullable(null);
        String secondOptionalStringValue = optional2.map(String::toUpperCase).orElse("default value");

        System.out.println(secondOptionalStringValue); // default value
    }

    public static void optionalExample6_orElseGet() {
        Optional<String> optional = Optional.ofNullable(null);

        String optionalValue = optional
                .map(String::toLowerCase)
                // with orElseGet , we can pass a supplier using a lambda expression containing any logic to get a default value from it
                .orElseGet(() -> {
                    // Here we can do any extra computation required to retrieve a value
                    return "w or l d".toUpperCase().replace(" ", "");
                });

        System.out.println(optionalValue); // WORLD
    }

    public static void optionalExample7_or() {
        Optional<Integer> intgerOptional = Optional.ofNullable(null);

        Integer value = intgerOptional
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

    public static void optionalExample8_orElseThrow() {
        Optional<String> optional = Optional.ofNullable(null);

        String value = optional
                .map(String::toUpperCase)
                // no-args orElseThrow will throw java.util.NoSuchElementException if there's no value present inside optional
                .orElseThrow();
    }

    public static void optionalExample9_orElseThrow_ExceptionManually() {
        Optional<String> optional = Optional.ofNullable(null);

        String value = optional
                .map(String::toUpperCase)
                // We can manually specify an exception to be thrown if the value inside the optional is not present
                .orElseThrow(IllegalStateException::new);
    }

    public static void optionalExample10_ifPresent() {
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


    public static void optionalExample11_ifPresentOrElse() {
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
        System.out.println("emptyOptionalExampleWithMethods()\n");
        OptionalBasicsDemo.emptyOptionalExampleWithMethods();

        System.out.println("\noptionalExample2()\n");
        OptionalBasicsDemo.optionalExample2();

        System.out.println("\noptionalExample3_orElse\n");
        OptionalBasicsDemo.optionalExample3_orElse();

        // Main.nullPointerExceptionScenario() // Throws a NullPointerException

        System.out.println("\noptionalExample4_ofNullable\n");
        OptionalBasicsDemo.optionalExample4_ofNullable();

        System.out.println("\noptionalExample5\n");
        OptionalBasicsDemo.optionalExample5();

        System.out.println("\noptionalExample6_orElseGet\n");
        OptionalBasicsDemo.optionalExample6_orElseGet();

        System.out.println("\noptionalExample7_or\n");
        OptionalBasicsDemo.optionalExample7_or();

        // System.out.println("\noptionalExample8_orElseThrow\n");
        // Main.optionalExample8_orElseThrow(); // will throw NoSuchElementException

        // System.out.println("\noptionalExample9_orElseThrow_ExceptionManually\n");
        // Main.optionalExample9_orElseThrow_ExceptionManually(); // will throw specified IllegalStateException

        System.out.println("\noptionalExample10_ifPresent\n");
        OptionalBasicsDemo.optionalExample10_ifPresent();

        System.out.println("\noptionalExample11_ifPresentOrElse\n");
        OptionalBasicsDemo.optionalExample11_ifPresentOrElse();
    }
}
