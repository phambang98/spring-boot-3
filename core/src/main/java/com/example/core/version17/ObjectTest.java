package com.example.core.version17;

import jakarta.persistence.Column;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ObjectTest {
    public static final String DEFAULT_FORMAT_DATETIME = "dd-MM-yyyy HH:mm:ss";

    public static void main(String[] args) throws Exception {
        ObjectTestSup objectTestSup = new ObjectTestSup();
        objectTestSup.setA1("a1");
        objectTestSup.setA3("a3");
        objectTestSup.setA5("a5");
        objectTestSup.setA7("a7");
        objectTestSup.setA8(new Date());

        for (Field field : objectTestSup.getClass().getDeclaredFields()) {
            String objectName = field.getName();
            String ObjNameValue;
            if (!objectName.isEmpty()) {
                String objectName1 = initCaps(objectName);
                ObjNameValue = "get" + objectName1;
                String colValue = getMethodValues(objectTestSup, ObjNameValue);

                if (colValue != null && !colValue.isEmpty()) {
                    System.out.println("colValue:" + colValue);
                }
            }
        }
        System.out.println("end");


        for (Field field : objectTestSup.getClass().getDeclaredFields()) {
            String objectName = field.getName();

            if (!objectName.isEmpty()) {
                // Trực tiếp sử dụng reflection để gọi getter của field mà không cần qua hàm getMethodValues
                field.setAccessible(true);
                Object colValueObj = field.get(objectTestSup);
                String colValue = null;

                if (colValueObj instanceof Date) {
                    colValue = dateToString((Date) colValueObj, DEFAULT_FORMAT_DATETIME);
                } else if (colValueObj != null) {
                    colValue = colValueObj.toString();
                }

                if (colValue != null && !colValue.isEmpty()) {
                    System.out.println("colValue:" + colValue);
                }
            }
        }
    }

    private static String getMethodValues(Object beanName, String sMethod) throws Exception {
        String sOutput = null;

        Method mOriginal = null;
        if (beanName != null && sMethod != null && !sMethod.isEmpty()) {
            Method[] methodArray = beanName.getClass().getMethods();
            for (Method methodName : methodArray) {
                if (methodName != null && methodName.getName().equals(sMethod)) {
                    mOriginal = methodName;
                    break;
                }
            }
        }
        if (mOriginal != null) {
            if (mOriginal.getName().contains("get")) {
                Object objOutput = mOriginal.invoke(beanName, (Object[]) null);
                if (objOutput instanceof Date) {
                    return dateToString((Date) objOutput, DEFAULT_FORMAT_DATETIME);
                }
                if (objOutput != null) {
                    sOutput = objOutput.toString();
                }
            }
        }

        return sOutput;
    }

    public static String initCaps(String original) {

        if (original == null) {
            throw new IllegalArgumentException("A non-null String must be supplied to CapitalizationUtils methods.");
        }
        if (original.length() == 0) {
            return original;
        }
        char[] originalChars = original.toCharArray();
        char firstLetter = Character.toUpperCase(original.charAt(0));
        originalChars[0] = firstLetter;
        return new String(originalChars).trim();
    }

    public static String dateToString(Date date, String format) {

        if (date == null || StringUtils.isEmpty(format)) return null;

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


}
