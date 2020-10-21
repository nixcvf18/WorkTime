package com.example.worktime.util;
import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
public class DatePickerDialogUtil {
    /*声明静态函数  用来展示日历控件*/
    public static void getDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener onDateSetListener) {
        Calendar calendar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
        }
        DatePickerDialog datePickerDialog = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            datePickerDialog = new DatePickerDialog(
                    context,
                    onDateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
        }
        datePickerDialog.show();
    }
    /*声明一个静态函数  用来获取当前日期的前天/
     */
    public static String getTheDayBeforeYesterday(String pattern) {
        long todayTime = System.currentTimeMillis();
        long timeDistance = 2 * 24 * 60 * 60 * 1000;
        long theDayBeforeYesterdayTime = todayTime - timeDistance;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date(theDayBeforeYesterdayTime);
        String theDayBeforeYesterdayDate = simpleDateFormat.format(date);
        return theDayBeforeYesterdayDate;
    }
    /*声明一个静态函数函数  用来获取当前日期的昨天*/
    public static String getYesterday(String pattern) {
        long todayTime = System.currentTimeMillis();
        long timeDistance = 1* 24 * 60 * 60 * 1000;
        long theYesterdayTime = todayTime - timeDistance;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date(theYesterdayTime);
        String theYesterdayDate = simpleDateFormat.format(date);
        return theYesterdayDate;
    }
    /*声明一个静态函数  用来获取当前日期*/
    public static String getCurrentDay(String pattern) {
        long todayTime = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date date = new Date(todayTime);
        String currentDate = simpleDateFormat.format(date);
        return currentDate;
    }
    /*声明一个静态函数 用来格式化从日历控件中选择的日期*/
    public static String getFormattedTheDateFromDatePickerDialog(int year, int month, int dayOfMonth) {
        int monthPlus=month+1;
        String formattedMonth=""+monthPlus;
        String formattedDay=""+dayOfMonth;
        if (monthPlus < 10) {
            formattedMonth = "0" + monthPlus;
        }
        if (dayOfMonth < 10) {
            formattedDay = "0" + dayOfMonth;
        }
        String formattedDate=year + "-" + formattedMonth + "-" + formattedDay;
        return formattedDate;
    }
}
