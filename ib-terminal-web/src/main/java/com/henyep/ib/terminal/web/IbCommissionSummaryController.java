package com.henyep.ib.terminal.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.henyep.ib.terminal.api.dto.request.ibcommission.summary.GetByTeamHeadRequestDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.GetCommissionDetailsResponseDto;
import com.henyep.ib.terminal.entity.SearchCommissonDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
@RequestMapping(value="/ibCommission")
@Controller
public class IbCommissionSummaryController {


	

	public static Logger getLogger() {
		return logger;
	}
	final static Logger logger = Logger.getLogger(IbCommissionSummaryController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/getSummary",method = RequestMethod.POST)
	public String getSummary(HttpServletRequest request,HttpServletResponse response,@Valid@ModelAttribute("searchDto")SearchCommissonDto searchDto,BindingResult result) throws ParseException{
		try {
			PrintWriter pw = response.getWriter();
			JsonUtil jsonUtil = new JsonUtil();
			logger.info(searchDto.getIb_code()+"  start date  "+searchDto.getStart_date()+"   enddate "+searchDto.getEnd_date());
			if(null==searchDto.getIb_code()||"".equals(searchDto.getIb_code())){
				logger.error("ib code is null ");
				return "redirect:/login.hyml";
			}
			if (result.hasErrors()){
				 List<ObjectError> errorList = result.getAllErrors();
		            for(ObjectError error : errorList){
		            	logger.info("errormessage:"+error.getDefaultMessage());
		            	String errorjson=error.getDefaultMessage().replace("{","").replace("}", "");
		                pw.write(errorjson);
		                break;
		            }
	        }else{
	        	GetByTeamHeadRequestDto requestBody = new GetByTeamHeadRequestDto();
				requestBody.setIb_code(searchDto.getIb_code());//10073019  10072913
	        	//requestBody.setIb_code(""+10073019);
	        	requestBody.setStart_date(TimeUtil.StringToDateShort(searchDto.getStart_date()));
	        	if(null!=searchDto.getEnd_date()&&""!=searchDto.getEnd_date()){
	        		requestBody.setEnd_date(TimeUtil.StringToDateShort(searchDto.getEnd_date()));
	        	}else{
	        		requestBody.setEnd_date(null);
	        		System.out.println("test test");
	        	}
				
				String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbTreeHeader(request),requestBody);
				logger.info("requestJson: "+requestJson);
				String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_COMMISSION_SUMMARY_PATH);
				logger.info("responseJosn: "+responseJson);
				DealErrorsUtil dealUtil= new DealErrorsUtil();
				dealUtil.<GetCommissionDetailsResponseDto>DealResultForAjax(requestJson, request);
				pw.write(responseJson);
	        }
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
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
