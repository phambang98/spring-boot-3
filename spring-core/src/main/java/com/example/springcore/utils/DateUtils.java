package com.example.springcore.utils;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

import java.text.ParseException;
import java.util.Date;

public class DateUtils {
    public static Date convertStringtoDate(String strDate, String patten) {
        if (strDate == null) return new Date();
        DateFormat format = new SimpleDateFormat(patten);
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            return new Date();
        }

    }

    public static String convertDateToString(Date date, String patten) {
        if (date == null) date = new Date();
        DateFormat format = new SimpleDateFormat(patten);
        return format.format(date);
    }
}
