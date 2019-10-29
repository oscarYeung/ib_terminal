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
import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.request.client.margin.in.out.ClientMarginInOutRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.client.margin.in.out.ClientMarginInOutResponseDto;
import com.henyep.ib.terminal.entity.ClientprofileDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
@RequestMapping(value="/client")
@Controller
public class ClientMarginOutController {
	
	final static Logger logger = Logger.getLogger(ClientMarginOutController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	
	@RequestMapping("/getClientMarginOut")
	public String getClientMarginOut(HttpServletRequest request,HttpServletResponse response){
		ClientprofileDto searchDto= new ClientprofileDto();
		searchDto.setStartDate(TimeUtil.getTimeShort(TimeUtil.getDateAfterDay(new Date(), 2)));
		searchDto.setEndDate(TimeUtil.getTimeShort(TimeUtil.getDateAfterDay(new Date(), 1)));
		request.setAttribute("searchDto", searchDto);
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
		return "client/marginOut";
	}
	@RequestMapping(value="/showClientMarginOut",method = RequestMethod.POST)
	public String showClientMarginOut(HttpServletRequest request,HttpServletResponse response, @Valid@ModelAttribute("searchDto") ClientprofileDto profiledto,BindingResult errors){
		Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
		try {
			if(errors.hasErrors()){
				 logger.info("profiledto fileds is error"+errors.getAllErrors().get(0));
				 return "client/marginOut";
			}
        	 JsonUtil jsonUtil = new JsonUtil();
        	 ClientMarginInOutRequestDto requestBody = new ClientMarginInOutRequestDto();
        	 requestBody.setStart_date(TimeUtil.StringToDateShort(profiledto.getStartDate()));
			 requestBody.setEnd_date(TimeUtil.StringToDateShort(profiledto.getEndDate()));
			 requestBody.setClient_code(profiledto.getClientCode());
			 requestBody.setIb_code(ibcode.toString());
			String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientMarginOutHeader(request),requestBody);
        	java.lang.reflect.Type type = new TypeToken<IbResponseDto<ClientMarginInOutResponseDto>>() {}.getType();
			logger.info("requestJson: "+requestJson);
			
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_CLIENT_MARGIN_OUT_PATH);
			logger.info("responseJosn: "+responseJson);
			IbResponseDto<ClientMarginInOutResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					List<ClientMarginInOutBean> responseBody=ibResponseDto.getBody().getList();
					logger.info("showClientMarginOut status: SUCCESS"+ibResponseDto.getBody()+" BalanceModel:");
					request.setAttribute("responseBody",responseBody);
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				}else{
					logger.info("showClientMarginOut error");
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
		RequestContext requestContext = new RequestContext(request);
		System.out.println("All "+requestContext.getMessage("ib.all"));
		if("*".equals(profiledto.getClientCode())){
			profiledto.setClientCode(requestContext.getMessage("ib.all"));
		}
		return "client/marginOut";
	}
}
