package com.henyep.ib.terminal.web;

import java.lang.reflect.Type;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.ib.IbEditRequestDto;
import com.henyep.ib.terminal.api.dto.request.user.IbProfileGetRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileEditResp;
import com.henyep.ib.terminal.api.dto.response.user.IbProfileGetResponseDto;
import com.henyep.ib.terminal.entity.Profiledto;
import com.henyep.ib.terminal.entity.RegisterDto;
import com.henyep.ib.terminal.interceptor.Token;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.register.RegUtil;

@Controller
public class ProfileController {

	final static Logger logger = Logger.getLogger(ProfileController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	@Token(save=true)
	@RequestMapping(value="/profile")
	public String profile(HttpServletRequest request,HttpServletResponse response,Model model ){
		try {
			RegisterDto req= new RegisterDto();
			RegUtil regutil= new RegUtil();
			
			logger.info("init profile info ");
			if(null!=request.getSession().getAttribute("profile")){
				request.getSession().removeAttribute("profile");
			}
			 if(null==request.getSession().getAttribute(ConstantFields.IB_BRANDCODE)){
				 logger.info("BrandCode is null");
				 return "redirect:/login.hyml";
			 }
			 IbProfileGetRequestDto body= new IbProfileGetRequestDto();
			 body.setBrandCode(request.getSession().getAttribute(ConstantFields.IB_BRANDCODE).toString());
			 body.setIbCode(request.getSession().getAttribute(ConstantFields.IB_CODE).toString());
			 
			 JsonUtil jsonUtil = new JsonUtil();
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getProfileHeader(request), body);
			logger.info("requestJson: "+requestJson);
			
			 String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.PROFILE_PATH);
			 logger.info("responseJosn: "+responseJson);
			 Type type = new TypeToken<IbResponseDto<IbProfileGetResponseDto>>() {}.getType();
			 IbResponseDto<IbProfileGetResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			 BaseResponseHeader responseHeader = ibResponseDto.getHeader();
				if(null!=responseHeader){
					Map<String, String> map = responseHeader.getErrorMap();
					 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						  logger.info("register status: SUCCESS");
						   request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
							IbProfileGetResponseDto profile=ibResponseDto.getBody();
							request.getSession().setAttribute("profile",profile);
							req=regutil.changeToRegisterDto(profile);
					 }else{
						 if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
							 return "redirect:/login.hyml";
						 }
					 }
				 }else{
					 return "redirect:/login.hyml"; 
				 }
			model.addAttribute("profilemodel",req);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info(" session langType"+request.getSession().getAttribute("langType")+" local:"+response.getLocale());
		return "profile";
	}
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
	@Token(remove=true)
	@RequestMapping(value="/doProfile",method = RequestMethod.POST)
	public String doProfile(HttpServletRequest request,HttpServletResponse response,@Valid @ModelAttribute("profilemodel") Profiledto profileDto,BindingResult errors,Model model){
		
		try {
			
			
			logger.info("error count:"+errors.getErrorCount());
			if(errors.getErrorCount()>0){
		            return "profile";
			}
			 logger.info("address  :  "+profileDto.getAddress());
			 IbProfileGetResponseDto profile=(IbProfileGetResponseDto)request.getSession().getAttribute("profile");
			 //body
			 RegUtil regUtil= new RegUtil();
			 IbEditRequestDto body= regUtil.changeToProfileRequest(profileDto,profile);
			 JsonUtil jsonUtil = new JsonUtil();
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getProfileHeader(request), body);
			 logger.info("requestJson: "+requestJson);
			 String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.PROFILEEDIT_PATH);
			 java.lang.reflect.Type type = new TypeToken<IbResponseDto<IbProfileEditResp>>() {}.getType();
			 
			IbResponseDto<IbProfileEditResp> ibResponseDto=jsonUtil.fromJson(respJson, type);
			logger.info("responseJosn: "+respJson);
			BaseResponseHeader ResponseHeader = ibResponseDto.getHeader();
			if(null!=ResponseHeader){
				Map<String, String> map = ResponseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(ResponseHeader.getStatus())){
					IbProfileEditResp resqBody=ibResponseDto.getBody();
					logger.info("edit profile status: SUCCESS,RecordNumberAffected:"+resqBody.getRecordNumberAffected());
					request.setAttribute("errormessage", "update.success");
					return "profile";
				}else{
					request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		model.addAttribute("profilemodel",profileDto);
	   return "profile";
	}

	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}

	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}

	public static Logger getLogger() {
		return logger;
	}
}
