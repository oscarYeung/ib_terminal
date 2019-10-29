package com.henyep.ib.terminal.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {


	
	public static final String SHOURTIME="yyyy-MM-dd";
	public static final String lONGTIME="yyyy-MM-dd HH:mm:ss";
	public static final String SIMPLETIME="yyyyMMddHHmmss";
	
	public static String getCurrDateFort(String pattern){
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(new Date());
	}
	public static String getCurrDateFort(String pattern,Date date){
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);
	}
	public static String getPatternDateFort(String pattern,String date){
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.format(date);
	}
	public static Date StringToDate(String pattern,String date) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat(pattern);
		return formater.parse(date);
	}
	public static Date StringToDateShort(String date) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat(SHOURTIME);
		return formater.parse(date);
	}
	public static Date StringToDateLong(String date) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat(lONGTIME);
		return formater.parse(date);
	}
	/**
	 * 
	 * @return
	 */
	public static String getTimeShort(){
		return getCurrDateFort(SHOURTIME);
	}
	
	public static String getTimeShort(Date date){
		return getCurrDateFort(SHOURTIME,date);
	}
	/**
	 * 
	 * @return
	 */
	public static String getTimeLong(){
		return getCurrDateFort(lONGTIME);
	}
	public static String getTimeLong(Date date){
		return getCurrDateFort(lONGTIME,date);
	}
	public static String getTimeLongStr(String date){
		return getPatternDateFort(lONGTIME,date);
	}
	 /**
	  * 
	  * @param date
	  * @return
	  */
	public static Timestamp getTimeStamp(Date date){
		Timestamp stamp=new Timestamp(date.getTime());
		return stamp;
	}
	 /**
     * 
     * @param d
     * @param day
     * @return
     */
	public static Date getDateAfterDay(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }
	public static Date getDateAfterMonth(Date d, int month) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) - month);
        return now.getTime();
    }
	public static Date getDateAfterYear(Date d, int year) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.YEAR, now.get(Calendar.YEAR) - year);
        return now.getTime();
    }
	 /**
     * return hours
     * @param d
     * @param hour
     * @return
     */
    public static Date getDateAfterHour(Date d,int hour){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR, now.get(Calendar.HOUR) - hour);
        return now.getTime();
    }
    /**
     * return minutes
     * @param d
     * @param minutes
     * @return
     */
    public static Date getDateAfterMinute(Date d,int minutes){
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) - minutes);
        return now.getTime();
    }
    /**
     * compare time
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareTime(String date1,String date2){
    	Date da1 =new Date(date1);
    	Date da2 =new Date(date2);
    	if(da1.after(da2)){
    		return true;
    	}
    	return false;
    }
  
    public static String time(String startTime, String endTime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(lONGTIME);// 格式化时间   
        Date outdate = sdf.parse(endTime);    
        Date indate =  sdf.parse(startTime);    
        long totalhours = (outdate.getTime()-indate.getTime())/(1000*60*60);//时   
        long totalminutes = (outdate.getTime()-indate.getTime())/(1000*60)-totalhours*60;//分   
        long totalseconds = (outdate.getTime()-indate.getTime())/(1000)-totalminutes*60;//秒   
        String total_time = totalhours+"时"+totalminutes+"分"+totalseconds+"秒";  
        return total_time;
    }
    

    public static String examRemainTime(String startTime, String endTime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat(lONGTIME);// 格式化时间   
        Date outdate = sdf.parse(endTime);    
        Date indate =  sdf.parse(startTime); 
        long totalminutes = (outdate.getTime()-indate.getTime())/(1000*60);//分   
        long totalseconds = (outdate.getTime()-indate.getTime())/(1000)-totalminutes*60;//秒  
        String remainTime = totalminutes+ "#"+ totalseconds;
        return remainTime;
    }
    /**
     * set zone time
     * @param date
     * @param zone
     * @return
     * @throws ParseException
     */
    public static Date getZoneDate(String date,String zone) throws ParseException{
    	 SimpleDateFormat sdf = new SimpleDateFormat(lONGTIME);// 格式化时间   
    	 sdf.setTimeZone(TimeZone.getTimeZone(zone));
    	return sdf.parse(date);
    }
    
    public static Date getShortDate(Date date) throws ParseException{
    	SimpleDateFormat sdf = new SimpleDateFormat(SHOURTIME);// 格式化时间   
    	String strTime=getCurrDateFort(SHOURTIME,date);
    	return sdf.parse(strTime);
    }
    /**
     * compare days
     */
    public static long compareDate(){
    	
        Calendar c1 = Calendar.getInstance();
        c1.set(2016, 8-1, 11);
      
        Calendar c2 = Calendar.getInstance();
        c2.set(2016, 9-1, 11);
       
        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();
       
        long days = (t2 - t1)/(24 * 60 * 60 * 1000);
        System.out.println("days"+days);
        return days;
    }
	public static Date testdate(Date date){
		 Date d=null;
		 try {
			 SimpleDateFormat sdf = new SimpleDateFormat(lONGTIME);// 格式化时间   
			  d= sdf.parse(getTimeLong());
			  System.out.println(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return d;
	}
	
	public static Date getMonthFirstDay(String date) throws Exception{
		  SimpleDateFormat format = new SimpleDateFormat(SHOURTIME);
            Calendar c = Calendar.getInstance();
			c.setTime(format.parse(date));
			c.add(Calendar.MONTH, 0);
	        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return c.getTime();
         
	}
	public static Date gettMonthLastDay(String date){
		SimpleDateFormat format = new SimpleDateFormat(SHOURTIME); 
        Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return ca.getTime();
      
	}
	
	
	public static void main(String[] args) throws ParseException {
		testdate(new Date());
		
		//Date str=StringToDate("yyyy-MM-dd HH:mm:ss","2015-12-12");
		//System.out.println(str);
		
		//Date str2=TimeUtil.getDateAfterYear(new Date(), -2);
		
		//System.out.println(str2);
		
		
	}
	
}
