package com.lifelineconnect.m8thubadmin.Utils;

import android.content.Context;
import android.support.annotation.VisibleForTesting;


import com.lifelineconnect.m8thubadmin.R;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeCalculation {

    public static String getTimeStringAgoSinceDate(Context context, Date date) {

           DateTime postDate = new DateTime(date, DateTimeZone.getDefault());
        return getTimeStringAgoSinceDateTime(context, postDate);
    }

    public static String getTimeStringAgoSinceDateChannle(Context context, String date) {
      // Log.e("Date---","Date----3--postDate--"+date);
        DateTime postDate =getCurrentTime(date);
      //  Log.e("Date---","Date----4--postDate--"+postDate);
        return getTimeStringAgoSinceDateTime(context, postDate);
    }

    private static String getTimeStringAgoSinceDateTime(Context context, DateTime dateTime) {
        DateTime now = new DateTime(DateTimeZone.getDefault());
        Period period = new Period(dateTime, now);
        //Log.e("Date---","Date----5--DateTimeZone--"+now+"---"+dateTime);
        String date;
        int count;
        //Log.e("Date---","Date----6--DateTimeZone--"+period.getYears()+"---"+period.getMonths()+"----"+period.getWeeks()+"---"
      //  +period.getDays()+"----"+period.getDays()+"----"+period.getHours()+"----"+period.getMinutes()+"----"+period.getSeconds());
        if (period.getYears() >= 1) {
            date = context.getResources().getQuantityString(R.plurals.date_years_ago, period.getYears());
            count = period.getYears();
        } else if (period.getMonths() >= 1) {
            date = context.getResources().getQuantityString(R.plurals.date_months_ago, period.getMonths());
            count = period.getMonths();
        } else if (period.getWeeks() >= 1) {
            date = context.getResources().getQuantityString(R.plurals.date_weeks_ago, period.getWeeks());
            count = period.getWeeks();
        } else if (period.getDays() >= 1) {
            date = context.getResources().getQuantityString(R.plurals.date_days_ago, period.getDays());
            count = period.getDays();
        } else if (period.getHours() >= 1) {
            date = context.getResources().getQuantityString(R.plurals.date_hours_ago, period.getHours());
            count = period.getHours();
        } else if (period.getMinutes() >= 1) {
            date = context.getResources().getQuantityString(R.plurals.date_minutes_ago, period.getMinutes());
            count = period.getMinutes();
        } else if (period.getSeconds() >= 3) {
            date = String.format(Locale.getDefault(), context.getString(R.string.date_seconds_ago), period.getSeconds());
            count = period.getSeconds();
        } else {
            return " " + context.getString(R.string.date_seconds_now);
        }
        //Log.e("Date---","Date----7--DateTimeZone--"+date+"----"+count);
        return String.format(Locale.getDefault(), date, count);
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    static boolean isMinutesDiffMax(DateTime dateTime1, DateTime dateTime2, int maxMinutes) {
        Period period = new Period(dateTime1, dateTime2);
        Duration duration = period.toDurationFrom(dateTime1);
        return Math.abs(duration.toStandardMinutes().getMinutes()) <= maxMinutes;
    }

    public static boolean wasMinutesAgoMax(Date date, int maxMinutes) {
        DateTime now = new DateTime(DateTimeZone.getDefault());
        DateTime dateTime = new DateTime(date, DateTimeZone.getDefault());
        return isMinutesDiffMax(dateTime, now, maxMinutes);
    }


    public static String getCurrentUTC(){

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms");
        outputFmt.setTimeZone(TimeZone.getTimeZone("UTC"));

        String formattedDate = outputFmt.format(time);
        return formattedDate;
    }
    public static DateTime getCurrentTime(String time){

String time1[] = time.split("\\.");
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(DateTimeZone.getDefault());

        //outputFmt.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().getDisplayName(true,TimeZone.SHORT)));
       // Log.e("Date---","Date----113--postDate--"+TimeZone.getDefault().getRawOffset());
        DateTime date = null;

            date = formatter.parseDateTime(time1[0]).plus(TimeZone.getDefault().getRawOffset());
        //date = date.plus(TimeZone.getDefault().getRawOffset());
        //Log.e("Date---","Date----3333--postDate--"+date);
       // long val = date.getTime()+TimeZone.getDefault().getRawOffset();

        DateTime postDate = new DateTime(date, DateTimeZone.getDefault());
        //Log.e("Date---","Date----4444--postDate--"+postDate);
        return postDate;
    }
}
