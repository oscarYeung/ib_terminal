package com.henyep.ib.terminal.server.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.henyep.ib.terminal.api.global.Constants;

public class DateUtil {
	
	public static SimpleDateFormat FORMATTER_DATE = new SimpleDateFormat(Constants.FORMAT_DATE);
	public static SimpleDateFormat FORMATTER_DATE_SHORT = new SimpleDateFormat(Constants.FORMAT_DATE_SHORT);
	public static SimpleDateFormat FORMATTER_DATE_TIME = new SimpleDateFormat(Constants.FORMAT_DATETIME);
	public static SimpleDateFormat FORMATTER_MONTH = new SimpleDateFormat(Constants.FORMAT_MONTH);
	public static SimpleDateFormat FORMATTER_DATE_DETAIL = new SimpleDateFormat(Constants.FORMAT_DATETIME_DETAILS);
	public static Date getCurrentDate(){
		
		return new Date();
	}
	
	public static Timestamp date2SqlTimestamp(java.util.Date date){
		if(date==null)return new Timestamp(System.currentTimeMillis());
		return new  Timestamp(date.getTime());
	}
	
	public static Date str2Date(String str) throws ParseException{
		DateFormat df = new SimpleDateFormat(Constants.FORMAT_DATE);
		return df.parse(str);
	}	
	
	public static String dateToStringByFormat(SimpleDateFormat format,Date d){
		return format.format(d);
	}
	
	
	public static Pair<Date, Date> getMonthDateRange(int year, int month) throws ParseException {
	    Date begining, end;

	    {
	        Calendar calendar = getCalendarForMonth(year, month);
	        calendar.set(Calendar.DAY_OF_MONTH,
	                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
	        setTimeToBeginningOfDay(calendar);
	        begining = calendar.getTime();
	    }

	    {
	        Calendar calendar = getCalendarForMonth(year, month);
	        calendar.set(Calendar.DAY_OF_MONTH,
	                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
	        setTimeToEndofDay(calendar);
	        end = calendar.getTime();
	    }

	    return Pair.of(begining, end);
	}
	
	public static Pair<Date, Date> getDayDateRange(Date day) throws ParseException {

		Date endTime = DateUtils.addMilliseconds(DateUtils.ceiling(day, Calendar.DATE), -1);
		Date startTime = DateUtils.truncate(day, Calendar.DATE);
		return Pair.of(startTime, endTime);
		
	}

	private static Calendar getCalendarForMonth(int year, int month) throws ParseException {
	    Calendar calendar = GregorianCalendar.getInstance();
	    int day = 1;
	    String dateString = String.format("%04d-%02d-%02d", year, month, day); 
	    Date date = str2Date(dateString);
	    calendar.setTime(date);
	    
	    return calendar;
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	}
	
	public static int getCurrentMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH)+1;
	}
	
	public static int getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	
	public static int getMonthByDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		// month is zero based (january = 0)
		month += 1;
		return month;
	}
	
	public static int getYearByDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		// month is zero based (january = 0)
		return year;
	}
	
	public static Date getLastDay(){
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
		
	}
	
	public static Date getMonthsBeforeDate(Date date, int month){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1 * month);
		return trimTime(cal.getTime());
	}
	
	public static Date trimTime(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public static boolean checkDateRangeOverlap(Date startDate1, Date endDate1, Date startDate2, Date endDate2){
		
		if(endDate1 != null && endDate2 != null){
			return (startDate1.before(endDate2) || startDate1.equals(endDate2)) && (endDate1.after(startDate2) || endDate1.equals(startDate2));
		}
		else if(endDate1 == null && endDate2 == null){
			return true;
		}
		else if(endDate1 == null){
			return endDate2.after(startDate1) || endDate2.equals(startDate1);
		}
		else if(endDate2 == null){
			return endDate1.after(startDate2) || endDate1.equals(startDate2);
		}
		else
			return true;
		
	}
	
}
