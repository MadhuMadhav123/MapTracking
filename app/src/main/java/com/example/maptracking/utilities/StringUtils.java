package com.example.maptracking.utilities;

import android.text.TextUtils;
import android.util.Base64;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String format2D(int i) {
        if (i > 0 && i < 10)
            return "0" + i;
        else if(i == 0)
            return "00";
        else
            return "" + i;
    }

    //String to Int
    public static int getInt(String str) {
        int value = 0;
        if (str == null || str.equalsIgnoreCase(""))
            return value;
        try {
            value = Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }


    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //String to long
    public static long getLong(String string) {
        long value = 0;

        if (string == null || string.equalsIgnoreCase(""))
            return value;

        try {
            value = Long.parseLong(string);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    //String to float
    public static float getFloat(String string) {
        float value = 0f;

        if (string == null || string.equalsIgnoreCase("")
                || string.equalsIgnoreCase("."))
            return value;

        try {
            value = Float.parseFloat(string);
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }

        return value;
    }

    //String to double
    public static double getDouble(String str) {
        double value = 0;

        if (str == null || str.equalsIgnoreCase(""))
            return value;

        try {
            value = Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    //String to boolean
    public static boolean getBoolean(String str) {
        boolean value = false;
        if (str == null || str.equalsIgnoreCase(""))
            return value;
        try {
            value = Boolean.parseBoolean(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    //Method to check email validation
    public static boolean isValidEmail(String string) {
        final Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                        + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
        Matcher matcher = EMAIL_ADDRESS_PATTERN.matcher(string);
        boolean value = matcher.matches();
        return value;
    }

    public static String encodeString(String str) {
        String encodedString = "";

        try {
            byte[] data = str.getBytes("UTF-8");
            encodedString = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encodedString;
    }

    public static String saveString(String str) {
        int len = str.length();
        int sum = 100;
        char[] encryptedChars = new char[len];
        for (int i = 0; i < len; i++)
            encryptedChars[i] = (char) ((int) str.charAt(i) + sum);
        char[] newNameStr = new char[len];
        for (int i = 0; i < len; i++)
            newNameStr[i] = encryptedChars[len - 1 - i];
        for (int i = 0; i < len; i++)
            encryptedChars[i] = newNameStr[i];
        return new String(encryptedChars);
    }

    public static String getString(String encrptredString) {
        int len = encrptredString.length();
        char[] decryptedChars = new char[len];
        for (int i = 0; i < len; i++)
            decryptedChars[i] = (char) ((int) encrptredString.charAt(i) - 100);
        char[] newNameStr = new char[len];
        for (int i = 0; i < len; i++)
            newNameStr[i] = decryptedChars[len - 1 - i];
        for (int i = 0; i < len; i++)
            decryptedChars[i] = newNameStr[i];
        return (new String(decryptedChars));
    }
    public  static  String get2Digits(int digit)
    {

        return  digit>9 ? digit+"" : "0"+digit ;
    }
    public  static  String adjust2DigitsHour(int digit)
    {
        if(digit == 0 )
            return "12";
        else
            return  digit>9 ? digit+"" : "0"+digit ;
    }

    public static boolean isNull(String str) {
        if(str==null)
            return true;
        else if(str.equalsIgnoreCase("null"))
            return true;
        else
            return false;

    }
    public static int getDiscountPrice(int discountPer,int originalPrice){
        int discountPrice=0;
        discountPrice=originalPrice-((originalPrice*discountPer)/100);
        return discountPrice;
    }
    public static Double getDiscountPrice(int discountPer,Double originalPrice){
        Double discountPrice;
        discountPrice=originalPrice-((originalPrice*discountPer)/100);
        return discountPrice;
    }
    public static Double getOnlyDiscountAmount(int discountPer,Double originalPrice){
        Double discountPrice;
        discountPrice= ((originalPrice*discountPer)/100);
        return discountPrice;
    }
    public static String convertKelvinToFahrenheit(float parseFloat) {
        float fahrenheit = (float) ((float) (parseFloat * (9d / 5)) - 459.67);
        String temparature = Math.round(fahrenheit) + "";
        return temparature;
    }

    public static String convertKelvinToCelsius(float temparature) {
        float celsius = temparature - 273.15F;//kelvin to centigrade conversion
        String tempCelsius = Math.round(celsius) + "";
        return tempCelsius;
    }
    public static double getMinValue(List<Float> values) {
       /* if (values.size() == 0) {
            return new double[2];
        }*/
        /*double min = values.get(0);
        int length = values.size();
        for (int i = 1; i < length; i++) {
            double value = values.get(i);
            min = Math.min(min, value);
        }*/
        return Collections.min(values);
    }
    public static double getMaxValue(List<Float> values) {
       /* if (values.size() == 0) {
            return new double[2];
        }*/
       /* double min = values.get(0);
        double max = min;
        int length = values.size();
        for (int i = 1; i < length; i++) {
            double value = values.get(i);
            max = Math.max(max, value);
        }*/
        return Collections.max(values);
    }
}
