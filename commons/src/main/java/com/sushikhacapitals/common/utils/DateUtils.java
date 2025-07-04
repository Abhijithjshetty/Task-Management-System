package com.sushikhacapitals.common.utils;


import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DateUtils {
    private static  final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private DateUtils() {

    }

    public static LocalDate localDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime localDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }


    public static Date getCurrentDate(){
        LocalDateTime now = LocalDateTime.now();
        Date startDate=Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        return startDate;
    }


    public static int compareDates(Date debitDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateString = formatter.format(new Date());
        Date currentDate = null;
        try {
            currentDate = formatter.parse(currentDateString);
        } catch (ParseException e) {
            log.error("ParseException ",e);
        }
        long diffInMillies = Math.abs(debitDate.getTime() - currentDate.getTime());
        Long diffDay = TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
        log.info("Difference in days for debit date and today's date is: {}", diffDay);
        return diffDay.intValue();
    }

    public static int convertHoursToMilliseconds(int debitDate){
        int dateConvert = debitDate*3600000;
        return  dateConvert;
    }

    public static int convertDaysToMilliseconds(int DEBIT_DATE_RANGE){
        int dateConvert = DEBIT_DATE_RANGE * 86400000;
        return  dateConvert;
    }


    public static long compareNotificationDate(Date notificationDate, Date date) {
        long diffInMillies = Math.abs(notificationDate.getTime() - date.getTime());
        Long diff = TimeUnit.MILLISECONDS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        Long diffDay = TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
        log.info("Difference in days for notification date and today's date is: {}", diffDay);
        return diff;
    }



    public static int compareDatesWithTime(Date debitDate, Date date)  {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateString = formatter.format(new Date());
        Date currentDate = null;
        try {
            currentDate = formatter.parse(currentDateString);
        } catch (ParseException e) {
            log.error("ParseException: ",e);
        }
        long diffInMillies = Math.abs(debitDate.getTime() - currentDate.getTime());
        Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        log.info("Difference in days for debit date and today's date is: {}", diff);
        return diff.intValue();
    }

    public static Long differenceInMilliSeconds(Date fromDate, Date toDate){
        Long diffInMillies = Math.abs(fromDate.getTime() - toDate.getTime());
        return diffInMillies;
    }

    public static Long secondsToMilliSeconds(Long seconds){
        return TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS);
    }


    public static String dateToString(Date date){
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            Date date1 = dateFormat.parse(date);
            return date1;
        }catch (ParseException parseException){
            return null;
        }
    }

    public static String getRollOverTableName(String tableMonth){
        LocalDate date = LocalDate.now();
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthValue() < 10 ? ("0"+date.getMonthValue()) : date.getMonthValue());
        return tableMonth+"_"+year+"_"+month;
    }





    public static int compareDatesWithoutTime(Date debitDate, Date date){
        Long diff = Math.abs(ChronoUnit.DAYS.between(localDate(debitDate), localDate(date)));
        return diff.intValue();
    }


    public static Date getDateAfterYears(long years){
        LocalDateTime localDateTime = dateToLocalDateTime(new Date());
        return localDateTimeToDate(localDateTime.plusYears(years));

    }

    public static String getMonth(Date date){
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        return monthFormat.format(date);
    }

    public static String getYear(Date date){
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return yearFormat.format(date);
    }


    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    public static LocalDate getLastDayOfMonth(String month, String year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return localDate(calendar.getTime());
    }


    public static Date toDateFromLocalDate(LocalDate localDate){
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public static int getMilliseconds(Date authDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateString = formatter.format(new Date());
        Date currentDate = null;
        try {
            currentDate = formatter.parse(currentDateString);
        } catch (ParseException e) {
            log.error("ParseException ",e);
        }
        long diffInMs = Math.abs(authDate.getTime() - currentDate.getTime());
        Long diff = TimeUnit.DAYS.convert(diffInMs, TimeUnit.MILLISECONDS);
        log.info("Difference in days for auth date and today's date is: {}", diff);
        return diff.intValue();
    }

    public static int monthToMilliseconds(int month){
        Long convertedValue = month * 2629800000L;
        Long diff = TimeUnit.DAYS.convert(convertedValue, TimeUnit.MILLISECONDS);
        log.info("Difference in days for month value and today's date is: {}", diff);
        return diff.intValue();
    }

    public static Boolean compareDateDiffInDays(Date passwordResetDate, Date currentDate) {
        Long diffInMillies = Math.abs(currentDate.getTime() - passwordResetDate.getTime());
        Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diff <= 2 ? false : true;
    }

    public static Long compareDateDiffInMins(Date passwordResetDate, Date currentDate) {
        Long diffInMillies = Math.abs(currentDate.getTime() - passwordResetDate.getTime());
        Long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return diffInMillies;
    }

    public static  Long diffInSec(int period,Long time) {
        Long lockoutInSec = period * 60l;
        Long diff = lockoutInSec - time;
        return diff;
    }
}
