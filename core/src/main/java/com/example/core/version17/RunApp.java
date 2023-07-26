package com.example.core.version17;


import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RunApp {

    public static void main(String[] args) {
//        String a = " ]][[[ |[B, S]1 |  [ cc  ]";
//        System.out.println(a.replaceAll("\\]|\\[|\\s+|cc|\\|", ""));
//        System.out.println(System.getProperty("sun.arch.data.model"));
        Calendar calendar = DateUtils.toCalendar(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
//        System.out.println(calendar.getTime().before(new Date()));

        Calendar calendar2 = DateUtils.toCalendar(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        System.out.println(convertMoneyToText("100000000000000000"));

    }

    public static String convertMoneyToText(String input) {
        String output = "";
        try {
            NumberFormat ruleBasedNumberFormat = new RuleBasedNumberFormat(new Locale("vi", "VN"), RuleBasedNumberFormat.SPELLOUT);
            output = ruleBasedNumberFormat.format(Long.parseLong(input)) + " Đồng";
        } catch (Exception e) {
            output = "không đồng";
        }
        return output.toUpperCase();
    }
}
