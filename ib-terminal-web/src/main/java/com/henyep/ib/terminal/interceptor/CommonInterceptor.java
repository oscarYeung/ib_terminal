package com.henyep.ib.terminal.interceptor;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.henyep.ib.terminal.utils.ConstantFields;


public class CommonInterceptor extends HandlerInterceptorAdapter{

	final static Logger logger = Logger.getLogger(CommonInterceptor.class);
	public String getCookiesValue(HttpServletRequest request){
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
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("preHandle url"+request.getRequestURL().toString()+" uri:"+request.getRequestURI());
		String languageType="";
		if("zh_CN".equals(getCookiesValue(request))){
			languageType="/chinese";
		}
		if(null!=request.getSession().getAttribute("langTypeLogin")&&"zh_CN".equals(request.getSession().getAttribute("langTypeLogin").toString())){
			languageType="/chinese";
			request.getSession().removeAttribute("langTypeLogin");
		}
		if("zh_CN".equals(request.getParameter("lang"))){
			languageType="/chinese";
			logger.info("langType  "+request.getParameter("lang"));
		}
		if("en_US".equals(request.getParameter("lang"))){
			languageType="";
			
		}
		logger.info("lang:"+request.getParameter("lang")+"  langTypeLogin"+request.getSession().getAttribute("langTypeLogin")+" cookie lang:"+getCookiesValue(request));
		request.getSession().setAttribute("langType", languageType);
		if(!"".equals(languageType)&&null!=languageType&&languageType.indexOf(ConstantFields.IB_CHINESE)>-1){
			logger.info("CURRENT_LANGUAGE languageType "+languageType);
			request.getSession().setAttribute(ConstantFields.CURRENT_LANGUAGE, ConstantFields.IB_CHINESE);
		}else{
			request.getSession().setAttribute(ConstantFields.CURRENT_LANGUAGE, ConstantFields.IB_ENGLISH);
		}
		
		Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
		if(null==ibcode||"".equals(ibcode)){
			logger.error("ib code is null ");
			response.sendRedirect("/login.hyml");
			//request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);  
			return false;
		}else{
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			
			
			if(validateTaken(request,response,handler)){
				return super.preHandle(request, response, handler);
			}else{
				response.sendRedirect("/profile.hyml");
				return false;
			}
			
		}
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		String languageType="";
		if("zh_CN".equals(response.getLocale().toString())){
			languageType="/chinese";
			
		}
		request.getSession().setAttribute("langType", languageType);
		logger.info("afterCompletion  local"+response.getLocale()+" cookie langType: "+getCookiesValue(request));
		super.afterCompletion(request, response, handler, ex);
	}
	private boolean validateTaken(HttpServletRequest request, HttpServletResponse response, Object handler){
		if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token. class );
            if (annotation != null ) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                	logger.info("save token :");
                    request.getSession( false ).setAttribute( "token" , UUID.randomUUID().toString());
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                	logger.info("remove token :");
                    if (isRepeatSubmit(request)) {
                        return false ;
                    }
                    request.getSession( false ).removeAttribute( "token" );
                }
            }
           
        }
		 return true;
	}
	private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken = (String) request.getSession( false ).getAttribute( "token" );
        if (serverToken == null ) {
            return true ;
        }
        String clinetToken = request.getParameter( "token" );
        if (clinetToken == null ) {
            return true ;
        }
        if (!serverToken.equals(clinetToken)) {
            return true ;
        }
        logger.info("remove serverToken :"+serverToken+"  clinetToken  "+clinetToken);
        return false ;
    }
}
