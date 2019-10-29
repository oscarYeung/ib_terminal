package com.henyep.ib.terminal.web;



import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.henyep.ib.terminal.entity.RegisterDto;
import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.communal.ConstantDefineCode;
import com.henyep.ib.terminal.api.dto.request.user.IbRegistReq;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.user.IbRegistResp;
import com.henyep.ib.terminal.operator.MiddleOperator;

import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.register.RegUtil;


@Controller
public class RegController {
	final static Logger logger = Logger.getLogger(RegController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/reg")
    public String reg(Model model) {
		RegisterDto dto = new RegisterDto();
		model.addAttribute("registerDto",dto);
		System.out.println("run to reg23");
		return "reg";
	}
	@RequestMapping(value="/doReg",method = RequestMethod.POST)
	public String doReg(HttpServletRequest request,HttpServletResponse response,@Valid@ModelAttribute("registerDto") RegisterDto regdto, BindingResult result){
		
		try {
			logger.info("user reg begin ");
			/*CommomUtil comm= new CommomUtil();
			if(!comm.isNumeric(regdto.getParentIbCode()+"")){
				logger.info(regdto.getParentIbCode()+"parentIbCode is not number ");
				request.setAttribute("errormessage", regdto.getParentIbCode()+"parentIbCode is not number");
				 return "reg";
			}*/
			
			if (result.hasErrors()){
	            List<ObjectError> errorList = result.getAllErrors();
	            for(ObjectError error : errorList){
	            	logger.info("errormessage:"+error.getDefaultMessage());
	                System.out.println("errormessage:"+error.getDefaultMessage());
	            }
	            return "reg";
	        }
			
			 JsonUtil jsonUtil = new JsonUtil();
			 java.util.Date date = new java.util.Date ();
			 logger.info(" parentIbCode: "+regdto.getParentIbCode());
			 regdto.setCreateTime(date);
			 regdto.setLastUpdateTime(regdto.getCreateTime());
			 regdto.setChineseName("chineseName");
			 regdto.setBrandCode(ConstantDefineCode.BRAND_CODE_CN);
			 regdto.setStatus(ConstantDefineCode.IB_USER_STATUS_INITIAL);
			 regdto.setLastUpdateUser(regdto.getUsername());
			// regdto.setSex("m");
			// regdto.setAddress("add");
			 regdto.setLanguage(ConstantDefineCode.SYS_LANGUAGE_ENGLISH);
			 RegUtil regutil= new RegUtil();
			 IbRegistReq ibRegBody= regutil.changeToRegRequest(regdto);
			 
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getRegHeader(request), ibRegBody);
			 
			logger.info("requestJson: "+requestJson);
			String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.Reg_PATH);
			java.lang.reflect.Type type = new TypeToken<IbResponseDto<IbRegistResp>>() {}.getType();
			
			logger.info("responseJosn: "+respJson);
			IbResponseDto<IbRegistResp> dto=jsonUtil.fromJson(respJson, type);
			logger.info("header:  "+dto.getHeader()+"body  :"+dto.getBody());
			if(null!=dto.getHeader()){
				Map<String, String> map = dto.getHeader().getErrorMap();
				if("SUCCESS".equals(map.get("001"))){
					IbRegistResp ibBody=dto.getBody();
					logger.info("register status: SUCCESS"+ibBody.getIbCode());
					
					request.getSession().setAttribute(ConstantFields.IB_CODE,ibBody.getIbCode());
					request.getSession().setAttribute(ConstantFields.IB_USERNAME, regdto.getUsername());
					request.getSession().setAttribute(ConstantFields.IB_BRANDCODE, regdto.getBrandCode());
					return "tool";
				}else{
					request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
				}
			}
		} catch (Exception e) {
			 logger.error("reg error!");
			e.printStackTrace();
			
		}
		return "reg";
	}
	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}
	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}
	
	
	public static void main(String[] args) {
		
		
	}
}
