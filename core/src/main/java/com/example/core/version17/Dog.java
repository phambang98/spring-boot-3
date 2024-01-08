package com.example.core.version17;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public final class Dog extends Vehicle {

    public Dog(String registrationNumber) {
        super(registrationNumber);
    }

    @Override
    public void test() {

    }

    public static void main(String[] args) {
        String input1 = "";
        String input2 = "213123";

        String input3 = " ";

        String regex = "";

        boolean contains200Whitespace1 = Pattern.matches(regex, input1);
        boolean contains200Whitespace2 = Pattern.matches(regex, input2);
        boolean contains200Whitespace3 = Pattern.matches(regex, input3);


        System.out.println("Input 1 contains 200 whitespaces: " + contains200Whitespace1);
        System.out.println("Input 2 contains 200 whitespaces: " + contains200Whitespace2);
        System.out.println("Input 2 contains 200 whitespaces: " + contains200Whitespace3);
        Map<String, String> mapSource = new HashMap<>();
        mapSource.put("source", null);
        System.out.println(mapSource.containsKey("source"));
        System.out.println(mapSource.containsKey("123"));
    }
}
