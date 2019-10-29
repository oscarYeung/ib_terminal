package com.henyep.ib.terminal.web;

import java.io.PrintWriter;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.request.clientProfile.GetClientProfileRequestDto;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.clientProfile.ClientMarginModel;
import com.henyep.ib.terminal.api.dto.response.clientProfile.GetClientProfileResponseDto;
import com.henyep.ib.terminal.entity.ClientprofileDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
import com.henyep.ib.terminal.web.validator.IbclientProfileSearchValidator;

@RequestMapping(value="/client")
@Controller
public class ClientProfileController {

	
	final static Logger logger = Logger.getLogger(ClientProfileController.class);
	
	@Resource(name = "IbclientProfileSearchValidator")
	private IbclientProfileSearchValidator ibclientProfileSearchValidator;
	@InitBinder()
	public void initBinder(DataBinder binder)
	{
		
		binder.addValidators(ibclientProfileSearchValidator);
	}
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/getIbClientPeriodSummary", method = RequestMethod.POST)
	public String getIbClientPeriodSummary(HttpServletRequest request,HttpServletResponse response,@Valid ClientprofileDto profiledto,BindingResult errors){
		String requestJson="";
		PrintWriter pw = null;
		try {
			pw=response.getWriter();
			 JsonUtil jsonUtil = new JsonUtil();
			 GetClientProfileRequestDto ibBody= new GetClientProfileRequestDto();
			 ibBody.setClient_code(profiledto.getClientCode());
			//不一样 是月报表
			 if(!profiledto.getStartDate().equals(profiledto.getEndDate())){
				 ibBody.setStart_date(TimeUtil.getMonthFirstDay(profiledto.getStartDate()));
				 ibBody.setEnd_date(TimeUtil.gettMonthLastDay(profiledto.getStartDate()));
			 }else{
				 ibBody.setStart_date(TimeUtil.StringToDateShort(profiledto.getStartDate()));
				 ibBody.setEnd_date(ibBody.getStart_date());
			 }
			 
			 logger.info("clientcode:"+profiledto.getClientCode()+"start "+ibBody.getStart_date()+"  end "+ibBody.getEnd_date());
			 requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientProfileHeader(request),ibBody);
			 logger.info("-- requestJson: "+requestJson);
			 String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_CLIENT_PROFILE_PATH);
			
			logger.info("--responseJosn: "+responseJson);
			 DealErrorsUtil dealUtil= new DealErrorsUtil();
			 String result=dealUtil.<GetClientProfileResponseDto>DealResultForAjax(responseJson,request);
			/*if(ConstantFields.SUCCESS.equals(result)){
					pw.write(responseJson);
					logger.info("getIbClientSummary  successs");
			}else{
					pw.write(result);
			}*/
			 pw.write(responseJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@RequestMapping(value="/ClientProfile", method = RequestMethod.GET)
	public String goToClientProfile(HttpServletRequest request,HttpServletResponse response){
		
	   	 try {
	   		
	   		Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			CommomClientController client = new CommomClientController();
			String result=client.getClientsCode(middlewareContext, ibcode.toString(), request);
			if(ConstantFields.LOGIN.equals(result)){
				 return "redirect:/login.hyml";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "client/myProfile";
	}
	@RequestMapping(value="/getIbClientProfile", method = RequestMethod.POST)
	public String getIbClientProfile(HttpServletRequest request,HttpServletResponse response,@Valid ClientprofileDto profiledto,BindingResult errors){
		
		String requestJson="";
		try {
			if(errors.hasErrors()){
				return "client/myProfile";
			}
			 JsonUtil jsonUtil = new JsonUtil();
			 GetClientProfileRequestDto ibBody= new GetClientProfileRequestDto();
			 ibBody.setClient_code(profiledto.getClientCode());
			 ibBody.setStart_date(TimeUtil.StringToDateShort(profiledto.getStartDate()));
			 ibBody.setEnd_date(ibBody.getStart_date());
			 
			 requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientProfileHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_CLIENT_PROFILE_PATH);
			
			
			java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetClientProfileResponseDto>>() {}.getType();
			logger.info("getIbClientProfile --- responseJosn: "+responseJson);
			IbResponseDto<GetClientProfileResponseDto> dto=jsonUtil.fromJson(responseJson, type);
			if(null!=dto.getHeader()){
				Map<String, String> map = dto.getHeader().getErrorMap();
				if(ConstantFields.SUCCESS.equals(map.get("status"))){
					List<ClientMarginModel> clientMargins =dto.getBody().getClient_margins();
					ClientDetailsBean clientDetail =dto.getBody().getClient_details();
					request.setAttribute("clientMargins", clientMargins);
					request.setAttribute("profile", clientDetail);
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, dto.getHeader().getSecretKey());
					logger.info("getIbClientProfile  successs");
				}else{
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
				}
			}
			 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "client/myProfile";
	}
	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}
	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}




}
