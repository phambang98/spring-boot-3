package com.example.core.version17.module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class ProcessNew extends AbstractProcess {

    @Override
    public void initData() {
        data = "ProcessNew";
    }

    public static void main(String[] args) {
        AbstractProcess processNew = new ProcessNew();
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");

        System.out.println(df.format(new Date(1703669163791l)));
        System.out.println(df.format(new Date(1703669206533l)));
    }
}
