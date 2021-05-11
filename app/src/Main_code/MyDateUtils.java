package com.assignment.mbas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.google.common.base.Preconditions.checkArgument;


public class MyDateUtils {
    public static String convertMilliSecondsToDateFormat(long milliSeconds) {


        // Create a DateFormatter object for displaying date in specified format. mmm ddth/ddrd
        String dateFormat = "MMM-dd hh:mm:ss a";
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, Locale.US);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);


        SimpleDateFormat mnthFormat = new SimpleDateFormat("MMM", Locale.US);
        String month = mnthFormat.format(calendar.getTime());

        SimpleDateFormat formatDayOfMonth = new SimpleDateFormat("d", Locale.US);
        int day = Integer.parseInt(formatDayOfMonth.format(calendar.getTime()));
        String daySuffix = getDayOfMonthSuffix(day);

        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.US);
        String timeStr = timeFormat.format(calendar.getTime());


//        return formatter.format(calendar.getTime());

        return month + " " + day + daySuffix + " " + timeStr;
    }

    public static int getDateDiff(long endTime) {
        Date fromDate, toDate;

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        int sec = calendar.get(Calendar.SECOND);
        calendar.set(year, month, day, hr, min, sec);
        fromDate = calendar.getTime();
        ////////////////////
        calendar.setTimeInMillis(endTime);
        toDate = calendar.getTime();

        if (fromDate == null || toDate == null)

        {
            return 0;
        }


        return (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24));
    }


    private static String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
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

    public static String stringToDateFormat(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        Date date = sdf.parse(strDate);
        return convertMilliSecondsToDateFormat(date.getTime());
    }
}
