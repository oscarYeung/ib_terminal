package com.henyep.ib.terminal.web;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
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
import com.henyep.ib.terminal.api.dto.db.IbClientLinkBean;
import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbClientSummaryRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequestDto;
import com.henyep.ib.terminal.api.dto.request.ibcommission.clientSummary.GetByIbCodeRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.link.GetIbClientLinkResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbClientSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbSummaryByDateRangeModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.GetByIbCodeResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.entity.SearchCommissonDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;

@RequestMapping(value="/ibCommission")
@Controller
public class IbSummaryController {


	final static Logger logger = Logger.getLogger(IbSummaryController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/getIbSummaryByDate")
	public String getIbSummaryByDate(HttpServletRequest request,HttpServletResponse response){
		try {
			
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			 JsonUtil jsonUtil = new JsonUtil();
			 GetIbSummaryRequestDto ibBody= new GetIbSummaryRequestDto();
			 ibBody.setIb_code(ibcode.toString());
			 ibBody.setTrade_date(TimeUtil.getShortDate(new Date()));
			 
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbSummaryByDateHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_SUMMARY_PATH);
			java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbSummaryResponseDto>>() {}.getType();
			
			logger.info("responseJosn: "+respJson);
			IbResponseDto<GetIbSummaryResponseDto> ibResponseDto=jsonUtil.fromJson(respJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					IbSummaryByDateRangeModel todaySummary=ibResponseDto.getBody().getTrade_day_ib_summary();
					IbSummaryByDateRangeModel monthSummary=ibResponseDto.getBody().getMonth_ib_summary();
					List<IbCommissionDetailsSupplementaryBean> evCommissionList=ibResponseDto.getBody().getEvCommissionList();
					IbSummaryByDateRangeModel lastMonthSummary=ibResponseDto.getBody().getLast_month_ib_summary();
					request.setAttribute("todaySummary", todaySummary);
					request.setAttribute("monthSummary", monthSummary);
					request.setAttribute("evCommissionList", evCommissionList);
					request.setAttribute("lastMonthSummary", lastMonthSummary);
					 request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					logger.info("getIbSummaryByDate status: SUCCESS evCommissionList:"+evCommissionList);
				}else{
					
					if(map.size()>0&&(map.containsKey(Constants.ERR_COMMON_SECRET_KEY_EMPTY)||map.containsKey(Constants.ERR_COMMON_API_SESSION_TIME_OUT))){
						return "redirect:/login.hyml";
					}
					request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
					
				}
			}
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "commission/summary";
	}
	@RequestMapping(value="/getIbSummaryByDate1")
	public String getIbSummaryByDate1(HttpServletRequest request,HttpServletResponse response){
		try {
			IbSummaryByDateRangeModel todaySummary=new IbSummaryByDateRangeModel();
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			 JsonUtil jsonUtil = new JsonUtil();
			 GetIbSummaryRequestDto ibBody= new GetIbSummaryRequestDto();
			 ibBody.setIb_code(ibcode.toString());
			 ibBody.setTrade_date(TimeUtil.getShortDate(new Date()));
			 
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbSummaryByDateHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_SUMMARY_PATH);
			java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbSummaryResponseDto>>() {}.getType();
			
			logger.info("responseJosn: "+respJson);
			IbResponseDto<GetIbSummaryResponseDto> ibResponseDto=jsonUtil.fromJson(respJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					todaySummary=ibResponseDto.getBody().getTrade_day_ib_summary();
					IbSummaryByDateRangeModel monthSummary=ibResponseDto.getBody().getMonth_ib_summary();
					List<IbCommissionDetailsSupplementaryBean> evCommissionList=ibResponseDto.getBody().getEvCommissionList();
					IbSummaryByDateRangeModel lastMonthSummary=ibResponseDto.getBody().getLast_month_ib_summary();
					request.getSession().setAttribute("todaySummary", todaySummary);
					request.getSession().setAttribute("monthSummary", monthSummary);
					request.getSession().setAttribute("evCommissionList", evCommissionList);
					request.getSession().setAttribute("lastMonthSummary", lastMonthSummary);
					 request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					logger.info("register status: SUCCESS");
				}else{
					
					if(map.size()>0&&(map.containsKey(Constants.ERR_COMMON_SECRET_KEY_EMPTY)||map.containsKey(Constants.ERR_COMMON_API_SESSION_TIME_OUT))){
						return "redirect:/login.hyml";
					}
					request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
					
				}
			}
			logger.info("startdate"+todaySummary.getStart_date()+"  enddate"+todaySummary.getEnd_date());
			getIbSummary1(request,response,TimeUtil.getTimeLong(todaySummary.getStart_date()),TimeUtil.getTimeLong(todaySummary.getStart_date()));
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "commission/summary1";
	}
	@RequestMapping(value="/getIbClientSummary1",method = RequestMethod.POST)
	public String getIbSummary1(HttpServletRequest request,HttpServletResponse response,@RequestParam String startDate,@RequestParam String endDate){
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
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientSummaryHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			 respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_GET_CLIENT_SUMMARY_PATH);
			 logger.info("responseJosn: "+respJson);
			 java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbClientSummaryResponseDto>>() {}.getType();
			 IbResponseDto<GetIbClientSummaryResponseDto> ibResponseDto=jsonUtil.fromJson(respJson, type);
				BaseResponseHeader responseHeader = ibResponseDto.getHeader();
				if(null!=responseHeader){
					Map<String, String> map = responseHeader.getErrorMap();
					 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						 GetIbClientSummaryResponseDto summaryBody=ibResponseDto.getBody();
						request.setAttribute("summaryBody", summaryBody);
						logger.info("getIbSummary1 success");
						request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					}else{
						request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
						if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
							 return "redirect:/login.hyml";
						 }
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "commission/summary1";
	}
	@RequestMapping(value="/getClientSummary1",method = RequestMethod.POST)
	public String getClientSummary1(HttpServletRequest request,HttpServletResponse response,@Valid@ModelAttribute("searchDto")SearchCommissonDto searchDto,BindingResult result) throws ParseException{
		try {
			
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
	            	 return "redirect:/login.hyml";
	               
	            }
	        }else{
	        	
				 GetByIbCodeRequestDto requestBody = new GetByIbCodeRequestDto();
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
				String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_COMMISSION_CLIENT_SUMMARY_PATH);
				logger.info("responseJosn: "+responseJson);
				java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetByIbCodeResponseDto>>() {}.getType();
				IbResponseDto<GetByIbCodeResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
				BaseResponseHeader responseHeader = ibResponseDto.getHeader();
				if(null!=responseHeader){
					Map<String, String> map = responseHeader.getErrorMap();
					 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						 GetByIbCodeResponseDto clientBody=ibResponseDto.getBody();
						request.setAttribute("clientBody", clientBody);
						request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					}else{
						request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
						if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
							 return "redirect:/login.hyml";
						 }
					}
				}
	        }
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return "commission/summary1";
	}
	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}
	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}


}
