package com.example.version17;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JavaNewVersion {

    public static void main(String[] args) throws Exception {
            String s= new String("1");
        System.out.println(s=="1");
            abc();
    }


    public static void abc() throws Exception {
        String a = null;
        a.toString();
    }


}
