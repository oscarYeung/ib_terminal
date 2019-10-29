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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.RequestContext;
import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.api.dto.request.trade.GetClientTradeHistoryRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.trade.GetClientTradeHistoryResponseDto;
import com.henyep.ib.terminal.entity.ClientprofileDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
@RequestMapping(value="/client")
@Controller
public final class ClientTradingHistory {
	

	//10072562 /1234567
	final static Logger logger = Logger.getLogger(ClientTradingHistory.class);
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	/*public void getClientsCode(String ibcode,HttpServletRequest request) throws ParseException{
	   	 JsonUtil jsonUtil = new JsonUtil();
	   	 GetIbClientMapRequestDto requestBody = new GetIbClientMapRequestDto();
	   	 requestBody.setTrade_date(TimeUtil.getShortDate(new Date()));
		 requestBody.setIb_code(ibcode.toString());
		 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientMarginOutHeader(request),requestBody);
    	java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbClientMapResponseDto>>() {}.getType();
		logger.info("requestJson: "+requestJson);
		String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_CLIENT_IB_CODES_PATH);
		logger.info("responseJosn: "+responseJson);
		IbResponseDto<GetIbClientMapResponseDto> dto=jsonUtil.fromJson(responseJson, type);
		if(null!=dto.getHeader()){
			Map<String, String> map = dto.getHeader().getErrorMap();
			if("SUCCESS".equals(map.get("status"))){
				List<IbClientMapBean> listClients=dto.getBody().getData();
				request.getSession().setAttribute("listClients",listClients);
			}else{
				logger.info("getClientsCode error");
				request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
			}
		}
		
	}*/
	@RequestMapping("/getTradingHisttory")
	public String getTradingHisttory(HttpServletRequest request,HttpServletResponse response){
		ClientprofileDto profiledto= new ClientprofileDto();
		profiledto.setStartDate(TimeUtil.getTimeShort(TimeUtil.getDateAfterDay(new Date(), 2)));
		profiledto.setEndDate(TimeUtil.getTimeShort(TimeUtil.getDateAfterDay(new Date(), 1)));
		request.setAttribute("searchDto", profiledto);
		
		Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
		if(null!=request.getSession().getAttribute("listClients")){
			request.getSession().removeAttribute("listClients");
		}
		try {
			CommomClientController client = new CommomClientController();
			client.getClientsCode(middlewareContext, ibcode.toString(), request);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "client/tradingHistory";
	}
	//http://127.0.0.1:8080/client/showTradingHistory.hyml?startDate=2016-10-1&endDate=2016-10-16&clientCode=366014
	@RequestMapping(value="/showTradingHistory",method = RequestMethod.POST)
	public String showTradingHistory(HttpServletRequest request,HttpServletResponse response, @Valid@ModelAttribute("searchDto") ClientprofileDto profiledto,BindingResult errors){
		try {
			if(errors.hasErrors()){
				 logger.info("profiledto fileds is error"+errors.getAllErrors().get(0));
				 return "client/tradingHistory";
			}
        	 JsonUtil jsonUtil = new JsonUtil();
        	 Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
        	 GetClientTradeHistoryRequestDto requestBody = new GetClientTradeHistoryRequestDto();
        	 requestBody.setStart_date(TimeUtil.StringToDateShort(profiledto.getStartDate()));
			 requestBody.setEnd_date(TimeUtil.StringToDateShort(profiledto.getEndDate()));
			 requestBody.setClient_code(profiledto.getClientCode());
			 requestBody.setIb_code(ibcode.toString());
			String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientMarginOutHeader(request),requestBody);
        	java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetClientTradeHistoryResponseDto>>() {}.getType();
			logger.info("requestJson: "+requestJson);
			
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_CLIENT_TRADE_HISTORY_PATH);
			logger.info("responseJosn: "+responseJson);
			IbResponseDto<GetClientTradeHistoryResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					GetClientTradeHistoryResponseDto responseBody=ibResponseDto.getBody();
					List<ClientTradeDetailsBean> history=ibResponseDto.getBody().getTrades();
					request.setAttribute("responseBody",responseBody);
					request.setAttribute("history",history);
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				}else{
					logger.info("showTradingHistory error");
					request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		RequestContext requestContext = new RequestContext(request);
		System.out.println("All "+requestContext.getMessage("ib.all"));
		if("*".equals(profiledto.getClientCode())){
			profiledto.setClientCode(requestContext.getMessage("ib.all"));
		}
		//request.setAttribute("searchDto", profiledto);
		return "client/tradingHistory";
	}

}
