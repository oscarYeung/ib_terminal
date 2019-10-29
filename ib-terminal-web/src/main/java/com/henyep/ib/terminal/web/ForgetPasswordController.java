package com.henyep.ib.terminal.web;

import java.lang.reflect.Type;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.password.ForgotPasswordRequestDto;
import com.henyep.ib.terminal.api.dto.request.password.ResetPasswordRequestDto;
import com.henyep.ib.terminal.api.dto.request.password.VerifyTokenRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.ForgotPasswordResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.PasswordUpdatedResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.VerifyTokenResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.entity.PasswordDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.CommomUtil;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.web.validator.PasswordValidator;
@Controller
public class ForgetPasswordController {
	final static Logger logger = Logger.getLogger(ForgetPasswordController.class);
	@Resource(name = "middleOperatorBean")
	MiddleOperator middlewareContext;
	
	@Resource(name="PasswordValidator")
	PasswordValidator passwordValidator;
	
	@InitBinder()
	public void initBinder(DataBinder binder)
	{
		logger.info("initBinder   passwordValidator");
		binder.addValidators(passwordValidator);
	}
	
	@RequestMapping("forget")
	   public String forgetpassword(HttpServletRequest request) {
		request.setAttribute("passwordModel", new PasswordDto());
			return "password/forgot";
		}
	
	
	@RequestMapping(value="/doForgotPassword",method = RequestMethod.POST)
    public String doforgetpwd(HttpServletRequest request,@Valid @ModelAttribute("passwordModel")PasswordDto passwordDto,BindingResult errors) {
		logger.info("ibLoginName:"+passwordDto.getUser_code()+"  password: "+passwordDto.getEmail());
		if(errors.hasErrors()){
	           return "password/forgot";
		}
		
		ForgotPasswordRequestDto body=new ForgotPasswordRequestDto();
		body.setEmail(passwordDto.getEmail());
		body.setUser_code(passwordDto.getUser_code());
		body.setUser_type(Constants.USER_TYPE_IB);
		if("zh_CN".equals(CommomUtil.getCookiesValue(request))){
			body.setLang(ConstantFields.IB_SC); 
		}else{
			body.setLang(ConstantFields.IB_EN); 
		}
		 JsonUtil jsonUtil = new JsonUtil();
		 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getPasswordHeader(request),body);
		 logger.info("request forgetjson:"+requestJson);
		 
		 String respforgetjson =middlewareContext.sendMessage(requestJson, ConstantFields.FORGETPASSWORD_PATH);
		 logger.info("response respforgetjson："+respforgetjson);
		 Type type=new TypeToken<IbResponseDto<ForgotPasswordResponseDto>>() {}.getType();
		 IbResponseDto<ForgotPasswordResponseDto> ibResponseDto = jsonUtil.fromJson(respforgetjson, type);
		 BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				 logger.info("forgetpassword SUCCESS status:"+ibResponseDto.getHeader().getErrorMap().get("status"));
				 //request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				 return "password/gotoMail";
			 }else{
				   logger.error("forgetpassword error:username and email is error");
				  /* if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }*/
				   request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
			 }		
		 }
			 return "password/forgot";
	}
	@RequestMapping("resetPassword")
    public String resetPassword(HttpServletRequest request) {
		String IdentifyingCode = request.getParameter("code");
		VerifyTokenRequestDto  body =new VerifyTokenRequestDto();
		body.setToken(IdentifyingCode);
		
		JsonUtil jsonUtil = new JsonUtil();
		String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getPasswordHeader(request),body);
		 logger.info("request forgetjson:"+requestJson);
		 String responseJson =middlewareContext.sendMessage(requestJson, ConstantFields.PASSWORD_TOKEN_PATH);
		 logger.info("response respforgetjson："+responseJson);
		 
		 Type type=new TypeToken<IbResponseDto<VerifyTokenResponseDto>>() {}.getType();
		IbResponseDto<VerifyTokenResponseDto> ibResponseDto = jsonUtil.fromJson(responseJson, type);
		BaseResponseHeader responseHeader = ibResponseDto.getHeader();
		if(null!=responseHeader){
			Map<String, String> map = responseHeader.getErrorMap();
			 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				 VerifyTokenResponseDto  verifyDto = ibResponseDto.getBody();
				 if(verifyDto.getSuccess()){
					
				 }
				 ResetPasswordRequestDto passwordDto=new ResetPasswordRequestDto();
				 passwordDto.setUser_code(verifyDto.getIb_Code());
				 passwordDto.setUser_type( verifyDto.getUser_type());
				 passwordDto.setToken(IdentifyingCode);
				 request.getSession().setAttribute("passwordDto", passwordDto);
				 //request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, ibResponseDto.getHeader().getSecretKey());
				 logger.info("forgetpassword SUCCESS status:"+ibResponseDto.getHeader().getErrorMap().get("status"));
				 return "password/reset";
			 }else{
				   logger.error("forgetpassword error:username and email is error");
				  /* if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }*/
				   request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
			 }		
		 }
		 return "password/gotoMail";
	}
	
	
	
	@RequestMapping(value="/doSetPassword",method = RequestMethod.POST)
    public String doSetPassword(HttpServletRequest request) {
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		boolean validateError=false;
		if(null==password||null==repassword||"".equals(password)||"".equals(repassword)){
			validateError=true;
		}
		if(null==request.getSession().getAttribute("passwordDto")){
			validateError=true;
		}
		if(!password.equals(repassword)){
			validateError=true;
		}
		ResetPasswordRequestDto body=(ResetPasswordRequestDto)request.getSession().getAttribute("passwordDto");
		if(body.getToken()==null||"".equals(body.getToken())||body.getUser_code()==null||"".equals(body.getUser_code())){
			validateError=true;
		}
		if(validateError){
			logger.error("setpassword password and repassword is error");
			 request.setAttribute("errormessage", "ib.password.error.return");
			 return "password/reset";
		}
		 body.setPassword(password);
		 
		 JsonUtil jsonUtil = new JsonUtil();
		 String requestJson = jsonUtil.toRequestEntityJson(HeaderUtil.getSetPasswordHeader(request),body);
		 logger.info("request setjson:"+requestJson);
		 
		 String responseJson =middlewareContext.sendMessage(requestJson, ConstantFields.SETPASSWORD_PATH);
		 logger.info("response respsetjson："+responseJson);
		 
		 Type type=new TypeToken<IbResponseDto<PasswordUpdatedResponseDto>>() {}.getType();
		 IbResponseDto<PasswordUpdatedResponseDto> ibResponseDto = jsonUtil.fromJson(responseJson, type);
		 BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				 logger.info("setpassword SUCCESS 001:"+ibResponseDto.getHeader().getErrorMap().get("001"));
				 request.getSession().removeAttribute("passwordDto");
				// request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, ibResponseDto.getHeader().getSecretKey());
				 return "password/resetOk";
			 }else{
				/* if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
					 return "redirect:/login.hyml";
				 }*/
				 
				 request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
			 }		
		 }
		 return "password/reset"; 
	}
	
}
