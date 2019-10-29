package com.henyep.ib.terminal.web;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.google.gson.reflect.TypeToken;

import com.henyep.ib.terminal.api.dto.request.password.ChangePasswordRequestDto;


import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.password.PasswordUpdatedResponseDto;

import com.henyep.ib.terminal.entity.ChangePasswordDto;

import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.web.validator.ChangePasswordValidator;

@Controller
public class ResetPassWordController {
	final static Logger logger = Logger.getLogger(ResetPassWordController.class);
	@Resource(name = "middleOperatorBean")
	MiddleOperator middlewareContext;
	
	@Resource(name="ChangePasswordValidator")
	ChangePasswordValidator changePasswordValidator;
	
	@InitBinder()
	public void initBinder(DataBinder binder)
	{
		logger.info("initBinder   passwordValidator");
		binder.addValidators(changePasswordValidator);
	}
	@RequestMapping(value="/changePassword")
    public String changePassword(HttpServletRequest request) {
		
		 return "password/changePassword"; 
	}
	@RequestMapping(value="/doChangePassword",method = RequestMethod.POST)
    public String doChangePassword(HttpServletRequest request,@Valid ChangePasswordDto changePasswordDto,BindingResult errors) {
		
		System.out.println("newpassword:"+changePasswordDto.getNewPassword()+" old "+changePasswordDto.getOldPassword()+" renew: "+changePasswordDto.getRenewPassword());
		if(errors.hasErrors()){
			 List<ObjectError> errorList = errors.getAllErrors();
	            for(ObjectError error : errorList){
	                System.out.println("errormessage:"+error.getDefaultMessage());
	                logger.error("doforgetpwd error:"+error.getDefaultMessage());
	            }
	            return "password/changePassword"; 
		}
		ChangePasswordRequestDto body=new ChangePasswordRequestDto();
		Object ibCode=request.getSession().getAttribute(ConstantFields.IB_CODE);
		if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getRenewPassword())){
			logger.error("setpassword password and repassword is error");
			 request.setAttribute("errormessage", "password.not.equal.to.repassword");
			 return "password/changePassword"; 
		}
		 body.setUser_code(ibCode.toString());
		 body.setOld_password(changePasswordDto.getOldPassword());
		 body.setNew_password(changePasswordDto.getNewPassword());
		 body.setUser_type(ConstantFields.IB_PASSWORD_TYPE);
		 JsonUtil jsonUtil = new JsonUtil();
		 String requestJson = jsonUtil.toRequestEntityJson(HeaderUtil.getChangePasswordHeader(request),body);
		 logger.info("requestJson:"+requestJson);
		 String responseJson =middlewareContext.sendMessage(requestJson, ConstantFields.CHANGE_PASSWORD_PATH);
		 logger.info("responseJson："+responseJson);
		 
		 Type type=new TypeToken<IbResponseDto<PasswordUpdatedResponseDto>>() {}.getType();
		 IbResponseDto<PasswordUpdatedResponseDto> ibResponseDto = jsonUtil.fromJson(responseJson, type);
		 BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				 logger.info("setpassword SUCCESS status:"+ibResponseDto.getHeader().getErrorMap().get("001"));
				 request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, ibResponseDto.getHeader().getSecretKey());
				 return "password/changeOk";
			 }else{
				 if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
					 return "redirect:/login.hyml";
				 }
			 }		
		 }
		 return "password/changePassword"; 
	}
	
	
	
	
	
}
