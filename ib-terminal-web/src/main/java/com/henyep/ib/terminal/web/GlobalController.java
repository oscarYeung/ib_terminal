package com.henyep.ib.terminal.web;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.support.RequestContext;

@Controller
public class GlobalController {
	final static Logger logger = Logger.getLogger(GlobalController.class);
	//http://127.0.0.1:8080/changeLanguage.hyml?langType=zh
	//http://127.0.0.1:8080/changeLanguage.hyml?langType=en
	@Autowired CookieLocaleResolver localeResolver;
	
	@RequestMapping(value="/changeLanguage", method = {RequestMethod.GET})
    public String test(HttpServletRequest request,Model model, @RequestParam(value="langType", defaultValue="en") String langType,HttpServletResponse response) throws UnsupportedEncodingException{
		System.out.println("getPathInfo  "+request.getPathInfo()+"   server path  "+request.getServletPath()+""+request.getRequestURL());
		String Referer=request.getHeader("Referer");
		if(null==Referer||"".equals(Referer)){
			Referer=request.getRequestURL().toString();
		}
		logger.info("Referer:"+Referer+" server"+request.getServerName());
		int beginIndex=Referer.indexOf(request.getServerName())+request.getServerName().length();
		String localStr="http://127.0.0.1:8080";
		if(request.getServerName().indexOf("127.0.0.1")>-1){
			beginIndex=Referer.indexOf(localStr)+localStr.length();
			logger.info("beginIndex:  "+beginIndex);
		}
		Referer=Referer.substring(beginIndex);
		logger.info("Referer go to:"+Referer);
		System.out.println(Referer);
		
          
		 if(langType.equals("zh")){
			 System.out.println("--zh--"+langType);
			 localeResolver.setLocale (request, response, Locale.CHINA);
         }else if(langType.equals("en")){
        	 System.out.println("--en--"+langType);
        	 localeResolver.setLocale (request, response, Locale.ENGLISH);
         }else{
        	 localeResolver.setLocale (request, response, Locale.CHINA);
            }
            RequestContext requestContext = new RequestContext(request);
           
            //从后台代码获取国际化信息
            System.out.println(requestContext.getMessage("money"));
            System.out.println(requestContext.getMessage("date"));
            System.out.println(requestContext.getMessage("message"));
		//String pathUrl=Referer+"?langType="+langType;///ib/getClientLinks.hyml&langType=en
		logger.info(" pathUrl  "+Referer);
       // return "redirect:"+Referer;
		return "test";
    }
	@RequestMapping(value="/changeLanguage1", method = {RequestMethod.GET})
    public String test1(HttpServletRequest request,Model model, @RequestParam(value="langType", defaultValue="en") String langType,HttpServletResponse response) throws UnsupportedEncodingException{
		System.out.println("getPathInfo  "+request.getPathInfo()+"   server path  "+request.getServletPath()+""+request.getRequestURL());
		
		String Referer=request.getHeader("Referer");
		if(null==Referer||"".equals(Referer)){
			Referer=request.getRequestURL().toString();
		}
		logger.info("Referer:"+Referer+" server"+request.getServerName());
		int beginIndex=Referer.indexOf(request.getServerName())+request.getServerName().length();
		String localStr="http://127.0.0.1:8080";
		if(request.getServerName().indexOf("127.0.0.1")>-1){
			beginIndex=Referer.indexOf(localStr)+localStr.length();
			logger.info("beginIndex:  "+beginIndex);
		}
		Referer=Referer.substring(beginIndex);
		logger.info("Referer go to:"+Referer);
		System.out.println(Referer);
		Cookie[] cookie= request.getCookies();
         for(Cookie cook : cookie){
        	 System.out.println(cook.getName()+cook.getValue());
        	 if("langType".equals(cook.getName())&&langType.equals("zh")){
        		cook.setValue("zh_CN");
        	 }else{
        		 cook.setValue("en_US");
        	 }
         } 
         Cookie cook= new Cookie("langType", "zh_CN");
		 if(langType.equals("zh")){
			 System.out.println("--zh--"+langType);
             Locale locale = new Locale("zh", "CN"); 
             (new CookieLocaleResolver()).setLocale (request, response, locale);
         }else if(langType.equals("en")){
        	 System.out.println("--en--"+langType);
        	 cook= new Cookie("langType", "en_US");
             Locale locale = new Locale("en", "US"); 
             (new CookieLocaleResolver()).setLocale (request, response, locale);
         }else{
            	(new CookieLocaleResolver()).setLocale (request, response, LocaleContextHolder.getLocale());
          }
		 response.addCookie(cook);
         RequestContext requestContext = new RequestContext(request);
           
            //从后台代码获取国际化信息
            System.out.println(requestContext.getMessage("money"));
            System.out.println(requestContext.getMessage("date"));
            System.out.println(requestContext.getMessage("message"));
		//String pathUrl=Referer+"?langType="+langType;///ib/getClientLinks.hyml&langType=en
		logger.info(" pathUrl  "+Referer);
       // return "redirect:"+Referer;
		return "test";
    }
	@RequestMapping("hello")
	public String test(){
		
		return "test2";
	}
    public static void main(String[] args) {
		String str="http://127.0.0.1:8080/mt4.html";
		String localstr="http://127.0.0.1:8080";
		if(str.indexOf(localstr)>-1){
			int index=str.indexOf(localstr);
			str=str.substring(index+localstr.length());
			System.out.println(str);
		}
	}

}
