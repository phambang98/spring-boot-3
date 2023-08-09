package com.example.core.version17;

import org.apache.commons.lang3.StringUtils;

public class StringProcess {
    public static void main(String[] args) {
        String ss = "tôi là 1 con người người à thế làm sao à thé thì   ông   thôi";
        System.out.println(lineLength(ss,50));
    }
    private static String lineLength(String str, int maxWidth) {
        StringBuilder txt = new StringBuilder();
        while (StringUtils.isNotEmpty(str)) {
            if (str.length() < maxWidth) {
                txt.append(str);
                break;
            } else if (str.charAt(maxWidth) == ' ') {
                txt.append(str, 0, maxWidth + 1).append("\n");
                str = str.substring(maxWidth + 1);
            } else {
                txt.append(str, 0, str.lastIndexOf(' ', maxWidth) + 1).append("\n");
                str = str.substring(str.lastIndexOf(' ', maxWidth) + 1);
            }
        }
        return txt.toString();
    }
}
