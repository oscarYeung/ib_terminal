package com.henyep.ib.terminal.web;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.api.dto.request.ib.client.map.GetIbClientMapRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.map.GetIbClientMapResponseDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;

public class CommomClientController {
	final static Logger logger = Logger.getLogger(CommomClientController.class);
	public String getClientsCode(MiddleOperator middlewareContext,String ibcode,HttpServletRequest request) throws ParseException{
	   	 JsonUtil jsonUtil = new JsonUtil();
	   	List<IbClientMapBean> listClients=null;
	   	GetIbClientMapRequestDto requestBody = new GetIbClientMapRequestDto();
		requestBody.setTrade_date(TimeUtil.getShortDate(new Date()));
		requestBody.setIb_code(ibcode);
		 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientMarginOutHeader(request),requestBody);
     	java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbClientMapResponseDto>>() {}.getType();
		logger.info("requestJson: "+requestJson);
		String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_CLIENT_IB_CODES_PATH);
		logger.info("responseJosn: "+responseJson);
		IbResponseDto<GetIbClientMapResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
		BaseResponseHeader responseHeader = ibResponseDto.getHeader();
		if(null!=responseHeader){
			Map<String, String> map = responseHeader.getErrorMap();
			 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				 listClients=ibResponseDto.getBody().getData();
				 request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				 request.getSession().setAttribute("listClients",listClients);
			}else{
				logger.info("getClientsCode error");
				if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
					 return ConstantFields.LOGIN;
				 }
			}
		}
		return null;
	}
	
	
}
