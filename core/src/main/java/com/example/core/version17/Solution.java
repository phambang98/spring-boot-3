package com.example.core.version17;

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập vào một chuỗi: ");
        String text = sc.nextLine();
        System.out.println(toInitCap(text));
    }


    public static String toInitCap(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (str.length() > 100) {
            return "";
        }
        str = str.trim().replaceAll("\\s+", "");
        String ressult = "";
        int sum = 0;
        Boolean flag = Character.isDigit(str.toCharArray()[0]);
        for (char ch : str.toCharArray()) {
            if (flag) {
                int number = Character.getNumericValue(ch);
                sum += number;
            } else {
                ressult += ch;
            }
        }
        if (flag) {
            return sum + "";
        }
        return ressult;


    }
}