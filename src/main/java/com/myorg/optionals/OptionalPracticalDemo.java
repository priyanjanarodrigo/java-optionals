package com.myorg.optionals;

import com.myorg.optionals.common.Person;

public class OptionalPracticalDemo {
    public static void main(String[] args) {
        // Scenario 1 - When the email is null -------------------------------------------------------------------------
        Person person1 = new Person("james", null);

        String person1Email = person1
                .getEmail()
                .map(String::toLowerCase)
                .orElse("EMAIL_NOT_AVAILABLE");

        System.out.println(person1Email); // EMAIL_NOT_AVAILABLE


        // Scenario 2 - When the email is available/present -------------------------------------------------------------
        Person person2 = new Person("jakson", "JAKSON@OUTLOOK.COM");

        String person2Email = person2.getEmail().map(String::toLowerCase).orElse("EMAIL_NOT_AVAILABLE");
        System.out.println(person2Email); // jakson@outlook.com


        // Scenario 3 - Validating the value inside the optional and getting it -----------------------------------------
        Person person3 = new Person("John Cena", "cena@wwe.com");

        // output will be "cena@wwe.com" as the email is available
        if (person3.getEmail().isPresent()) {
            String person3Email = person3.getEmail().get();
            System.out.println(person3Email);
        } else {
            System.out.println("EMAIL_NOT_AVAILABLE");
        }

        Person person4 = new Person("John Cena", null);

        // output will be "EMAIL_NOT_AVAILABLE" as the email is not available/present inside the optional
        if (person4.getEmail().isPresent()) {
            String person3Email = person3.getEmail().get();
            System.out.println(person3Email);
        } else {
            System.out.println("EMAIL_NOT_AVAILABLE");
        }
    }
}
