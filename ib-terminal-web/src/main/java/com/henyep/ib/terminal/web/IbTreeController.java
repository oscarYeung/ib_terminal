package com.henyep.ib.terminal.web;


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

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.ib.tree.IbTreeRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.lead.GetIbLeadsResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.tree.GetIbTreeResponse;
import com.henyep.ib.terminal.api.dto.response.ib.tree.web.IbTreeData;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryTrimmedResponseDto;
import com.henyep.ib.terminal.entity.SearchCommissonDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
@RequestMapping(value="/ibCommission")
@Controller
public class IbTreeController {


	final static Logger logger = Logger.getLogger(IbTreeController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/getTree")
	public String initTree(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		try {
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			 IbTreeRequestDto requestBody = new IbTreeRequestDto();
			 requestBody.setTrade_date(TimeUtil.getShortDate(new Date()));
			// requestBody.setStart_date(TimeUtil.getShortDate(TimeUtil.getDateAfterDay(new Date(),19)));
			 requestBody.setIb_code(ibcode.toString());
			String resultError=sendTreeMessage(requestBody,request);
			if(ConstantFields.LOGIN.equals(resultError)){
				 return "redirect:/login.hyml";
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} 
		SearchCommissonDto dto = new SearchCommissonDto();
		dto.setEnd_date(TimeUtil.getTimeShort(new Date()));
		dto.setStart_date(TimeUtil.getTimeShort(TimeUtil.getDateAfterMonth(new Date(), 3)));
		request.setAttribute("searchDto",dto);
		return "ibPerformance";
	}
	@RequestMapping(value="/searchTree",method=RequestMethod.POST)
	public String SearchTree(HttpServletRequest request,HttpServletResponse response,@Valid@ModelAttribute("searchDto") SearchCommissonDto searchDto,BindingResult result) throws ParseException{
		try {
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			if (result.hasErrors()){
	            List<ObjectError> errorList = result.getAllErrors();
	            for(ObjectError error : errorList){
	            	logger.info("errormessage:"+error.getDefaultMessage());
	                System.out.println("errormessage:"+error.getDefaultMessage());
	            }
	            return "ibPerformance";
	        }
			 IbTreeRequestDto requestBody = new IbTreeRequestDto();
			 requestBody.setTrade_date(TimeUtil.getShortDate(new Date()));
			// requestBody.setStart_date(TimeUtil.StringToDateLong(searchDto.getStart_date()));
			 requestBody.setIb_code(ibcode.toString());
			// requestBody.setIb_code("INT");//test 10072913
			 String resultError=sendTreeMessage(requestBody,request);
				if(ConstantFields.LOGIN.equals(resultError)){
					 return "redirect:/login.hyml";
				 }
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "ibPerformance";
	}
	public String sendTreeMessage(IbTreeRequestDto requestBody,HttpServletRequest request){
		JsonUtil jsonUtil = new JsonUtil();
		 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbTreeHeader(request),requestBody);
		logger.info("requestJson: "+requestJson);
		String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_TREE_PATH);
		logger.info("responseJosn: "+responseJson);
		
		java.lang.reflect.Type type = new TypeToken<IbResponseDto<IbTreeData>>() {}.getType();
		IbResponseDto<IbTreeData> ibResponseDto=jsonUtil.fromJson(responseJson, type);
		BaseResponseHeader responseHeader = ibResponseDto.getHeader();
		String result=null;
		if(null!=responseHeader){
			Map<String, String> map = responseHeader.getErrorMap();
			 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				int startPos = responseJson.indexOf("\"body\":");
				startPos += 8;
				responseJson = responseJson.substring(startPos); 	
				responseJson = responseJson.substring(0, responseJson.length() - 2);
				logger.info("responseJosn body: "+responseJson);
				request.setAttribute("jsonString", responseJson);
				request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, ibResponseDto.getHeader().getSecretKey());
			}else{
				
				if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
					result=ConstantFields.LOGIN;
				 }
			}
		}
		return result;
		/*if(responseJson.indexOf("001")>-1&&responseJson.indexOf("SUCCESS")>-1){
			int startPos = responseJson.indexOf("\"body\":");
			startPos += 8;
			responseJson = responseJson.substring(startPos); 	
			responseJson = responseJson.substring(0, responseJson.length() - 2);
			logger.info("responseJosn body: "+responseJson);
			request.setAttribute("jsonString", responseJson);
			request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, ibResponseDto.getHeader().getSecretKey());
		}*/
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
