package com.example.nitesh.playo.utils;


import android.content.Context;

import com.example.nitesh.playo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static final String NEWS_LISTING = "yyyy-dd-MM'T'HH:mm:ss";

    public static Date parse(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(date);
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String getRelativeTime(Context context, Date date) {
        String time = "";
        long timeInMillis = Calendar.getInstance().getTimeInMillis();

        long diff, minutes, hours, days;
        diff = timeInMillis - date.getTime();

        days = diff / (24 * 60 * 60 * 1000);

        if (days > 1) {
            time = String.format(context.getString(R.string.days_ago), days);
        } else if (days == 1) {
            time = context.getString(R.string.one_day_ago);
        } else {
            hours = diff / (60 * 60 * 1000);
            if (hours > 1) {
                time = String.format(context.getString(R.string.hours_ago), hours);
            } else if (hours == 1) {
                time = context.getString(R.string.one_hour_ago);
            } else {
                minutes = diff / (60 * 1000);
                if (minutes > 1) {
                    time = String.format(context.getString(R.string.minutes_ago), minutes);
                } else
                    time = context.getString(R.string.one_minute_ago);
            }
        }
        return time;
    }

    public static Date getCurrentDate() {
        return new Date();
    }

}

