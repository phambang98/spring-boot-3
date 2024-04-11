package com.example.core.version17;

import org.owasp.encoder.Encode;

import java.math.BigDecimal;
import java.util.*;
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

        String ss = "abcasd ";
        String cc = ss + "1";

        System.out.println(cc.substring(ss.length(), cc.length()));

        System.out.println("ccc " + null instanceof String);
    }

    public static Object[] arrayObjectEncodeHtml(Object[] objects) {
        Object[] arrayResult = new Object[objects.length];
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Number) {
                arrayResult[i] = new BigDecimal(Encode.forHtml(String.valueOf(objects[i])));
            } else {
                arrayResult[i] = Encode.forHtml(String.valueOf(objects[i]));
            }
        }
        return arrayResult;
    }


}
