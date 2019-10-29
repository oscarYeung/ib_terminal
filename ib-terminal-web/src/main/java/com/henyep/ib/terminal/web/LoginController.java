package com.henyep.ib.terminal.web;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.reflect.TypeToken;

import com.henyep.ib.terminal.api.dto.request.ib.IbLoginRequestDto;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.login.IbLoginResponseDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;


@Controller
public class LoginController {
//http://127.0.0.1:8888/IbTerminalWeb/login.do
	final static Logger logger = Logger.getLogger(RegController.class);
	@Resource(name = "middleOperatorBean")
	MiddleOperator middlewareContext;
	@RequestMapping("login")
    public String login(HttpServletRequest request,SessionStatus sessionStatus) {
		request.getSession().removeAttribute(ConstantFields.IB_STATE);
		request.getSession().removeAttribute(ConstantFields.IB_BRANDCODE);
		request.getSession().removeAttribute(ConstantFields.IB_USERNAME);
		request.getSession().removeAttribute(ConstantFields.IB_CODE);
		request.getSession().removeAttribute(ConstantFields.IB_SECRET_KEY);
		request.getSession().removeAttribute("accountModel");
		request.getSession().removeAttribute("ClientAccountModels");
		request.getSession().removeAttribute("balanceModel");
		request.getSession().invalidate();
		sessionStatus.isComplete();
		logger.info("login  langTypeLogin:"+getCookiesValue(request));
		request.getSession().setAttribute("langTypeLogin", getCookiesValue(request));
		
		return "login";
	}
	@RequestMapping("dologin")
    public String dologin(HttpServletRequest request,@Valid @ModelAttribute("loginDto") IbLoginRequestDto loginDto,BindingResult result) {
		if(result.hasErrors()){
            return "login";
		}
		
		 IbLoginRequestDto body=new IbLoginRequestDto();
		 body.setIbLoginName(loginDto.getIbLoginName());
		 body.setIbLoginPassWord(loginDto.getIbLoginPassWord());
		 logger.info("loginDto.getIbLoginPassWord()");
		 JsonUtil jsonUtil = new JsonUtil();
		 String loginjson = jsonUtil.toRequestEntityJson(HeaderUtil.getIbLoginHeader(request), body);
		 logger.info("request loginjson:"+loginjson);
		 String resploginjson =middlewareContext.sendMessage(loginjson, ConstantFields.Login_PATH);
		 logger.info("response loginjson："+resploginjson);
		 
		 IbResponseDto<IbLoginResponseDto> ibResponseDto = jsonUtil.fromJson(resploginjson, new TypeToken<IbResponseDto<IbLoginResponseDto>>() {}.getType());
		 if(ibResponseDto!=null){	
			 Map<String, String> map = ibResponseDto.getHeader().getErrorMap();
			 System.out.println("status "+ibResponseDto.getHeader().getStatus()+"--suce"+ConstantFields.SUCCESS);
			// System.out.println("if true"+ibResponseDto.getHeader().getMessageType().equals(ConstantFields.SUCCESS));
			 if((ConstantFields.SUCCESS).equals(ibResponseDto.getHeader().getStatus())){
				 IbLoginResponseDto ibLoginResponseDto = ibResponseDto.getBody();
				// ibLoginResponseDto.setAccountInformations(ibLoginResponseDto.getAccountInformations());
				 request.getSession().setAttribute(ConstantFields.IB_STATE, ibLoginResponseDto.getIbStatus());
				 request.getSession().setAttribute(ConstantFields.IB_BRANDCODE, ibLoginResponseDto.getBrandCode());
				 request.getSession().setAttribute(ConstantFields.IB_USERNAME, ibLoginResponseDto.getIbLoginName());
				 request.getSession().setAttribute(ConstantFields.IB_CODE, ibLoginResponseDto.getIbCode());
				 request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, ibResponseDto.getHeader().getSecretKey());
				 request.getSession().setAttribute(ConstantFields.IB_IS_WHITE_LABEL_USER, ibResponseDto.getBody().getIs_white_label_user());
				 request.getSession().setAttribute(ConstantFields.WL_COMPANY_CODE, ibResponseDto.getBody().getWl_company_code());
				 request.getSession().setAttribute(ConstantFields.WL_PLATFORM, ibResponseDto.getBody().getWl_platform());
				 request.getSession().setAttribute(ConstantFields.WL_REGISTRATION_TYPE, ibResponseDto.getBody().getWl_registration_type());
				 request.getSession().setAttribute(ConstantFields.WL_SERVER_CODE, ibResponseDto.getBody().getWl_server_code());
				 request.getSession().setAttribute("ibLoginResponseDto", ibLoginResponseDto);
				 
				 logger.info("login SUCCESS status:"+map.get("002")+" map002 "+ibResponseDto.getHeader().getErrorMap().get("001"));
				 return "redirect:index.hyml";
			 }else{
				
				 request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
				 return "login"; 
			 }
			
			 
		 }else{
			 logger.error("login error:username and password is error");
			 request.getSession().setAttribute("flag", "2");
			 return "login"; 
		 }		
		
	}
	public String getCookiesValue(HttpServletRequest request){
		
		
		Cookie[] cookies=request.getCookies();
		String cookieValue="";
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("langType".equals(cookie.getName())){
					System.out.println("login cookie"+cookieValue);
					cookieValue=cookie.getValue();
					break;
				}
			}
		}
		return cookieValue;
	}
	@RequestMapping("logOut")
	public String logOut(HttpServletRequest request, SessionStatus sessionStatus){
		return "redirect:login.hyml";
	}
}
