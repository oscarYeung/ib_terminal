package com.henyep.ib.terminal.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbClientSummaryRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.clientSummary.GetByIbCodeRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbClientSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbCommissionDetailsResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.GetByIbCodeResponseDto;
import com.henyep.ib.terminal.entity.SearchCommissonDto;

import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;

@RequestMapping(value="/ibCommission")
@Controller
public class IbClientSummary {



	final static Logger logger = Logger.getLogger(IbClientSummary.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	@RequestMapping(value="/getIbClientSummary",method = RequestMethod.POST)
	public String getIbClientSummary(HttpServletRequest request,HttpServletResponse response,@RequestParam String startDate,@RequestParam String endDate){
		String respJson="";
		PrintWriter pw = null;
		try {
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			if(null==startDate||null==endDate||"".equals(startDate)||"".equals(endDate)){
				startDate=TimeUtil.getTimeShort();
				endDate=startDate;
			}
			logger.info(startDate+" endDate"+startDate+"ibcode"+ibcode);
			 JsonUtil jsonUtil = new JsonUtil();
			 pw = response.getWriter();
			 GetIbClientSummaryRequestDto ibBody= new GetIbClientSummaryRequestDto();
			 ibBody.setIb_code(ibcode.toString());
			 ibBody.setStart_date(TimeUtil.StringToDateLong(startDate));
			 ibBody.setEnd_date(TimeUtil.StringToDateLong(endDate));
			// logger.info("  startDate  "+startDate+"  endDate"+endDate);
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientSummaryHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			 respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_NEW_COMMISSION_SUMMARY_PATH);
			 logger.info("responseJosn: "+respJson);
			 DealErrorsUtil dealUtil= new DealErrorsUtil();
			 String result=dealUtil.<GetIbClientSummaryResponseDto>DealResultForAjax(respJson, request);
			 pw.write(respJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value="/getIbCommissionSummaryDetails",method = RequestMethod.POST)
	public String getIbCommissionSummaryDetails(HttpServletRequest request,HttpServletResponse response,@Valid@ModelAttribute("searchDto")SearchCommissonDto searchDto,BindingResult result) throws ParseException{
		try {
			PrintWriter pw = response.getWriter();
			 JsonUtil jsonUtil = new JsonUtil();
			logger.info(searchDto.getIb_code()+"  start date  "+searchDto.getStart_date()+"   enddate "+searchDto.getEnd_date());
			if(null==searchDto.getIb_code()||"".equals(searchDto.getIb_code())){
				logger.error("ib code is null ");
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
	        	
	        	GetIbClientSummaryRequestDto requestBody = new GetIbClientSummaryRequestDto();
				 if(null!=searchDto.getEnd_date()&&""!=searchDto.getEnd_date()){
		        		requestBody.setEnd_date(TimeUtil.StringToDateShort(searchDto.getEnd_date()));
		        }else{
		        		requestBody.setEnd_date(null);
		        }
				 requestBody.setIb_code(searchDto.getIb_code());//test 10072913
				//requestBody.setIb_code(searchDto.getIb_code());
				requestBody.setStart_date(TimeUtil.StringToDateShort(searchDto.getStart_date()));
				String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbTreeHeader(request),requestBody);
				logger.info("requestJson: "+requestJson);
				String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_COMMISSION_SUMMARY_DETAIL_PATH);
				logger.info("responseJosn: "+responseJson);
				
				java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbCommissionDetailsResponseDto>>() {}.getType();
				IbResponseDto<GetIbCommissionDetailsResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
				BaseResponseHeader responseHeader = ibResponseDto.getHeader();
				if(null!=responseHeader){
					Map<String, String> map = responseHeader.getErrorMap();
					 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					}
				}
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
	
	
	@RequestMapping(value="/old/getIbClientSummary",method = RequestMethod.POST)
	public String getOldIbClientSummary(HttpServletRequest request,HttpServletResponse response,@RequestParam String startDate,@RequestParam String endDate){
		String respJson="";
		PrintWriter pw = null;
		try {
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			if(null==startDate||null==endDate||"".equals(startDate)||"".equals(endDate)){
				startDate=TimeUtil.getTimeShort();
				endDate=startDate;
			}
			logger.info(startDate+" endDate"+startDate+"ibcode"+ibcode);
			 JsonUtil jsonUtil = new JsonUtil();
			 pw = response.getWriter();
			 GetIbClientSummaryRequestDto ibBody= new GetIbClientSummaryRequestDto();
			 ibBody.setIb_code(ibcode.toString());
			 ibBody.setStart_date(TimeUtil.StringToDateLong(startDate));
			 ibBody.setEnd_date(TimeUtil.StringToDateLong(endDate));
			// logger.info("  startDate  "+startDate+"  endDate"+endDate);
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientSummaryHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			 respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_GET_CLIENT_SUMMARY_PATH);
			 logger.info("responseJosn: "+respJson);
			 DealErrorsUtil dealUtil= new DealErrorsUtil();
			 String result=dealUtil.<GetIbClientSummaryResponseDto>DealResultForAjax(respJson, request);
			 pw.write(respJson);
		} catch (Exception e) {
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

 

}
