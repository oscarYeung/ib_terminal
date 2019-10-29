package com.henyep.ib.terminal.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CommomUtil {
	public static String getCookiesValue(HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		String cookieValue=null;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("langType".equals(cookie.getName())){
					cookieValue=cookie.getValue();
					break;
				}
			}
		}
		return cookieValue;
	}
	public  boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
	
	public  static Map<String, String> getMarginoutStatus(){
		Map< String, String> map = new HashMap< String, String>();
		map.put("P", "Pending");
		map.put("E", "Excute");
		map.put("C", "Cancle");
		map.put("R", "Rejected");
		return map;
	}
}
