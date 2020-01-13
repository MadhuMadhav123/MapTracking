package com.example.maptracking.utilities;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class  CalendarUtils {
    public static final String DATE_MAIN_AM_PM = "yyyy-MM-dd hh:mm a";
    public static final String HOUR_12 = "hh";
    public static final String HOUR_24 = "HH";
    public static final String MINUTE = "mm";
    public static final String DATE_PATTERN6 = "yyyy-MM-dd";
    public static final String DATE_PATTERN7 = "dd-MM-yyyy";
    private static final String DATE_PATTERN1 = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_PATTERN4 = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN5 = "yyyy-MM-dd HH:mm";
    public static final String DATE_MAIN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YEAR_MONTH_DATE = "yyyy-MM-dd";
    private static final String DATE_PATTERN2 = "dd/MM/yyyy hh:mm:ss";
    public static final String DATE_STD_PATTERN2 = "dd-MM-yyyy HH:mm ";
    public static final String TIME_PATTERN = "HH:mm";
    public static final String TIME_PATTERN_12 = "hh:mm";
    public static final String DAY_DATE_MONTH_FULL = "EEEE MMMM yyyy";
    public static final String DAY_MONTH_FULL = "EEEE, dd MMM";
    public static final String DATE_MONTH_FULL_YEAR = "dd MMMM yyyy";
    public static final String DAY_FULL = "EEEE";
    public static final String DAY_MEDIUM = "EEE";
    public static final String DATE_FULL = "dd";
    public static final String DATE_FORMAT_2 = "MMM dd, hh:mm a";
    public static final String DATE_FORMAT_MONTH_DATE = "MMM dd";
    public static final String YEAR_FULL = "yyyy";
    public static final String MONTH_FULL = "MMMM";
    public static final String MONTH_MEDIUM = "MMM";
    public static final String HOUR_MIN_SEC = "hh:mm:ss";
    public static final String HOUR24_MIN_SEC = "HH:mm:ss";
    public static final String HOUR_MIN_AM_PM = "hh:mm a";
    public static final String MIN_SEC = "mm:ss";
    public static final String AM_PM = "a";

    public static final String DATE_PATTERN3 = "MMM yyyy";
    public static final String DATE_PATTERN_MONTH = "MMM";

    //Get specific formatted Date(Date) from milliseconds(long)
    public static Date getFormattedDate(long dateInMilliseconds) {
        Date resultDate;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN1, Locale.US);
        resultDate = new Date(dateInMilliseconds);
        Date date = null;
        try {
            date = sdf.parse(sdf.format(resultDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //Get specific formatted Date(Date) from milliseconds
    public static Date getDate(long milliSeconds) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(DAY_MONTH_FULL, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        try {
            date = formatter.parse(formatter.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //Get specific formatted Date(Date) from milliseconds
    public static Date get24HrFormatTimeByMilliSeconds(String format, long milliSeconds) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.US);  ///HOUR24_MIN_SEC
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        try {
            date = formatter.parse(formatter.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    //Get milliseconds(long) from Date(Date)
    public static long getTimeStamp(Date date) {
        try {
            return date.getTime() / 1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Get total duration in  hh:mm:ss format from milliseconds(long)
    public static String getDuration(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        if (hours < 1)
            return minutes + ":" + seconds;
        return hours + ":" + minutes + ":" + seconds;
    }

    public static long getMilliseondsFromDate(int date, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(date, month, year);
        return calendar.getTimeInMillis();
    }

    public static String getMonthAndYear(long milliseconds) {
        String sdf = new SimpleDateFormat(DAY_MONTH_FULL).format(milliseconds);
        return sdf;
    }

    public static String getMonth(long milliseconds) {
        String sdf = new SimpleDateFormat(DATE_PATTERN_MONTH).format(milliseconds);
        return sdf;
    }

    public static String getHourAndMinutes(long milliseconds) {
        String sdf = new SimpleDateFormat(TIME_PATTERN, Locale.US).format(milliseconds);
        return sdf;
    }

    public static String getDatePatternByMilliSecs(long milliseconds) {
        String sdf = new SimpleDateFormat(DATE_PATTERN5,Locale.US).format(milliseconds);
        return sdf;
    }

    public static String getData(long milliseconds, String format) {
        String sdf = new SimpleDateFormat(format, Locale.US).format(milliseconds);
        return sdf;
    }

    public static String getDataWithLocale(long milliseconds, String format) {
        String sdf = new SimpleDateFormat(format).format(milliseconds);
        return sdf;
    }
    public static String getFullDateMonthYear(long milliseconds) {
        String date = new SimpleDateFormat(DATE_FULL,Locale.US).format(milliseconds);
        String year = new SimpleDateFormat(YEAR_FULL,Locale.US).format(milliseconds);
        String monthFull = new SimpleDateFormat(MONTH_FULL,Locale.US).format(milliseconds);

        return date + getPref(StringUtils.getInt(date)) + " " + monthFull + " " + year;
    }

    public static String getPref(int d) {

        if (d > 3 && d < 21)
            return "th";
        switch (d % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }

    }

    public static String getMessageBasedOnTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String message = "";
        if (timeOfDay >= 0 && timeOfDay < 12) {
            message = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            message = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 24) {
            message = "Good Evening";
        }

        return message;
    }

    public static String getCurrentDateForLogs() {
        String sdf = new SimpleDateFormat(DATE_STD_PATTERN2, Locale.US).format(System.currentTimeMillis());
        return sdf;
    }

    public static String getCurrentDateAndtime() {
        String sdf = new SimpleDateFormat(DATE_MAIN, Locale.US).format(System.currentTimeMillis());
        return sdf;
    }
    public static String getDateByMills(long millis,String outFormate) {
        String sdf = new SimpleDateFormat(outFormate, Locale.US).format(millis);
        return sdf;
    }
    public static String getCurrentDateAndtime(String outFormate) {
        String sdf = new SimpleDateFormat(outFormate, Locale.US).format(System.currentTimeMillis());
        return sdf;
    }

    public static String getCurrentTime(String format) {
        String sdf = new SimpleDateFormat(format, Locale.US).format(System.currentTimeMillis()); //DATE_MAIN
        return sdf;
    }
    public static long getTimeMilliSeconds(String updatedAt) {
        if ((!TextUtils.isEmpty(updatedAt)) && (!updatedAt.equalsIgnoreCase("null"))) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN4, Locale.US);
            try {
                Date date = sdf.parse(updatedAt);
                return (date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return Calendar.getInstance().getTimeInMillis();
            }

        }
        return Calendar.getInstance().getTimeInMillis();
    }
    public static long getTimeMilliSecondsForDay(String updatedAt,String format) {
        if ((!TextUtils.isEmpty(updatedAt)) && (!updatedAt.equalsIgnoreCase("null"))) {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            try {
                Date date = sdf.parse(updatedAt);
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
                return Calendar.getInstance().getTimeInMillis();
            }

        }
        return Calendar.getInstance().getTimeInMillis();
    }


    public static long getTimeMilliSeconds(String updatedAt, String format) {
        if ((!TextUtils.isEmpty(updatedAt)) && (!updatedAt.equalsIgnoreCase("null"))) {
            SimpleDateFormat sdf = new SimpleDateFormat(CalendarUtils.DATE_YEAR_MONTH_DATE + " " + format, Locale.US);
            try {
                String yyyymmdd = CalendarUtils.getData(Calendar.getInstance().getTimeInMillis(), CalendarUtils.DATE_YEAR_MONTH_DATE);
                Date date = sdf.parse(yyyymmdd + " " + updatedAt);
                return (date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return Calendar.getInstance().getTimeInMillis();
            }
        }
        return Calendar.getInstance().getTimeInMillis();
    }

    public static long get24hrsTimeMilliSeconds(String updatedAt) {
        if ((!TextUtils.isEmpty(updatedAt)) && (!updatedAt.equalsIgnoreCase("null"))) {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN5, Locale.US);
            try {
                Date date = sdf.parse(updatedAt);
                return (date.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
                return Calendar.getInstance().getTimeInMillis();
            }
        }
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getConversion(String updatedAt, String inputFormat, String outputFormat) {
        SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat, Locale.US);
        SimpleDateFormat outFormat = new SimpleDateFormat(outputFormat, Locale.US);
        try {
            Date date = inFormat.parse(updatedAt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            return outFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return outFormat.format(Calendar.getInstance().getTime());
        }
    }

    public static String getConversionWithLocale(String updatedAt, String inputFormat, String outputFormat) {
        SimpleDateFormat inFormat = new SimpleDateFormat(inputFormat);
        SimpleDateFormat outFormat = new SimpleDateFormat(outputFormat);
        try {
            Date date = inFormat.parse(updatedAt);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            return outFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return outFormat.format(Calendar.getInstance().getTime());
        }
    }

    public static long getCurrentDataAndTime() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String get12HrFormatFromMilliseconds(long milliseconds) {
        String sdf = new SimpleDateFormat(DAY_FULL).format(milliseconds);
        return sdf;
    }

    public static boolean isToday(long timeInMilliseconds) {
        Calendar sourceDate = Calendar.getInstance();
        sourceDate.setTimeInMillis(timeInMilliseconds);
        Calendar now = Calendar.getInstance();
        if (now.get(Calendar.DATE) == sourceDate.get(Calendar.DATE))
            return true;
        return false;
    }
    public static String getDate(String strDate) {
        String changedDateFormat = "";
        try {
            SimpleDateFormat objSDF = new SimpleDateFormat(DATE_PATTERN4);
            Date objParseDate = objSDF.parse(strDate);
            changedDateFormat = getData(objParseDate.getTime(),DATE_PATTERN6);
        }catch (Exception e){
            e.printStackTrace();
        }
        return changedDateFormat;
    }

    public static boolean isEndDateLessthan(String startDate,String endDate) {
        try {
            if(startDate.isEmpty())
                return true;
            else if(startDate.equalsIgnoreCase(endDate))
                return false;
            SimpleDateFormat objSDF = new SimpleDateFormat(DATE_PATTERN4);
            Date dt_1 = objSDF.parse(startDate);
            Date dt_2 = objSDF.parse(endDate);
            if (dt_1.compareTo(dt_2) > 0) // Date 2 occurs after Date 1
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static int getDayOfWeekInteger(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }
}



