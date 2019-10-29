package com.henyep.ib.terminal.web;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContext;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.marginout.CancelMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.GetMaxWithdrawalRequestDto;
import com.henyep.ib.terminal.api.dto.request.marginout.InsertMarginOutRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMarginOutResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.GetMaxWithdrawalResponseDto;
import com.henyep.ib.terminal.api.dto.response.marginout.InsertMarginOutResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.entity.InsertMarginOutDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.CommomUtil;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;
import com.henyep.ib.terminal.web.validator.MarginOutValidator;
@Controller
@RequestMapping("/margin")
public class MarginOutController {

	final static Logger logger = Logger.getLogger(MarginOutController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@Resource(name = "MarginOutValidator")
	private MarginOutValidator marginOutValidator;
	
	@InitBinder()
	public void initBinder(DataBinder binder)
	{
		logger.info("initBinder   marginOutValidator");
		binder.addValidators(marginOutValidator);
	}
	public   Map<String, String> getMarginoutStatus(HttpServletRequest request){
		Map< String, String> map = new HashMap< String, String>();
		RequestContext requestcontext =new RequestContext(request);
		map.put("P", requestcontext.getMessage("ib.pending"));
		map.put("E", requestcontext.getMessage("ib.executed"));
		map.put("C", requestcontext.getMessage("ib.cancle"));
		map.put("A", requestcontext.getMessage("ib.approved"));
		map.put("R", requestcontext.getMessage("ib.rejected"));
		return map;
	}
	public void setMarginoutStatus(HttpServletRequest request){
		Map<String,String> statusmap =getMarginoutStatus(request);
		request.setAttribute("statusMap", statusmap);
	}
	@RequestMapping("/getMaxWithdrawal")
	public String getMaxWithdrawal(HttpServletRequest request,HttpServletResponse response){
		try {
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			if(null!=request.getSession().getAttribute(ConstantFields.IB_WITHDRAWAL_CURRENCY)){
				request.getSession().removeAttribute(ConstantFields.IB_WITHDRAWAL_CURRENCY);
			}
        	 JsonUtil jsonUtil = new JsonUtil();
        	 GetMaxWithdrawalRequestDto requestBody = new GetMaxWithdrawalRequestDto();
			 requestBody.setIb_code(ibcode.toString());
			
			String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbWithdrawerHeader(request),requestBody);
        	java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetMaxWithdrawalResponseDto>>() {}.getType();
			logger.info("requestJson: "+requestJson);
			
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_WITHDRAWAL_MAX_PATH);
			logger.info("responseJosn: "+responseJson);
			IbResponseDto<GetMaxWithdrawalResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					GetMaxWithdrawalResponseDto ibBody=ibResponseDto.getBody();
					logger.info("getMaxWithdrawal status: SUCCESS"+ibResponseDto.getBody().getMax_withdrawal()+" BalanceModel:"+ibResponseDto.getBody().getIb_code());
					InsertMarginOutDto marginOut=getMarginOutDto(ibBody);
					request.setAttribute("withdrawalModel",marginOut);
					request.getSession().setAttribute(ConstantFields.IB_WITHDRAWAL_CURRENCY,marginOut.getCurrency());
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				}else{
					logger.info("getMaxWithdrawal error");
					request.setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
					if(map.containsKey(Constants.ERR_COMMON_SECRET_KEY_EMPTY)||map.containsKey(Constants.ERR_COMMON_API_SESSION_TIME_OUT)){
						return "redirect:/login.hyml";
					}
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
					
				}
			}
			
			String result=getMarginOut(request,response,ibcode.toString());
			if(ConstantFields.LOGIN.equals(result)){
				 return "redirect:/login.hyml";
			 }
			setMarginoutStatus(request);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return "withdrawal";
	}
	@RequestMapping("/getMarginOut")
	public String getMarginOut(HttpServletRequest request,HttpServletResponse response,String account){
		String result="";
		try {
        	 JsonUtil jsonUtil = new JsonUtil();
        	 GetMarginOutRequestDto requestBody = new GetMarginOutRequestDto();
			 requestBody.setStartDate(TimeUtil.getDateAfterMonth(new Date(),3));
			 requestBody.setEndDate(new Date());
			 MarginOutBean marginOutBean=new MarginOutBean();
			 marginOutBean.setAccount(account);
			 requestBody.setMarginOutBean(marginOutBean);
			 String requestJson=jsonUtil.toRequestEntityJsonNotNull(HeaderUtil.getWithdrawerHistoryHeader(request),requestBody);
        	 java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetMarginOutResponseDto>>() {}.getType();
			 logger.info("requestJson: "+requestJson);
			
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_WITHDRAWAL_MARGINOUT_PATH);
			logger.info("responseJosn: "+responseJson);
			IbResponseDto<GetMarginOutResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader ResponseHeader = ibResponseDto.getHeader();
			if(null!=ResponseHeader){
				Map<String, String> map = ResponseHeader.getErrorMap();
				System.out.println(map.get("10004")+"error ---"+map.get("10009"));
				 if((ConstantFields.SUCCESS).equals(ResponseHeader.getStatus())){
					GetMarginOutResponseDto ibBody=ibResponseDto.getBody();
					logger.info("getMarginOut status: SUCCESS"+ibResponseDto.getBody().getMarginOutBeans());
					request.setAttribute("withdrawlHistry",ibBody);
					
				}else{
					logger.info("getMarginOut error");
					result=ConstantFields.LOGIN;
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}
	@RequestMapping("/cancelMarginOut")
	public String cancelMarginOut(HttpServletRequest request,HttpServletResponse response,@RequestParam Integer marginOutId){
	
		try {
		
        	 JsonUtil jsonUtil = new JsonUtil();
        	 CancelMarginOutRequestDto requestBody = new CancelMarginOutRequestDto();
        	 requestBody.setMargin_out_id(marginOutId);
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getCancleWithdrawerHeader(request),requestBody);
        	 java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetMarginOutResponseDto>>() {}.getType();
			 logger.info("requestJson: "+requestJson);
			 if(null!=request.getSession().getAttribute("errorWithdrawal")){
				request.getSession().removeAttribute("errorWithdrawal");
			 }
			 String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_WITHDRAWAL_CANCEL_MARGINOUT_PATH);
			 logger.info("responseJosn: "+responseJson);
			 IbResponseDto<GetMarginOutResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			 BaseResponseHeader ResponseHeader = ibResponseDto.getHeader();
				if(null!=ResponseHeader){
					Map<String, String> map = ResponseHeader.getErrorMap();
					 if((ConstantFields.SUCCESS).equals(ResponseHeader.getStatus())){
					//pw.write("001");
					request.getSession().setAttribute("errorWithdrawal","SUCCESS");
					logger.info("cancelMarginOut status: SUCCESS");
				}else{
					logger.info("cancelMarginOut error");
					request.getSession().setAttribute("errorWithdrawal", DealErrorsUtil.getErrorInfo(map));
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
		return "redirect:/margin/getMaxWithdrawal.hyml";
	}
	//http://127.0.0.1:8080/margin/InsertMarginOut.hyml
	@RequestMapping("/insertPendingMarginOut")
	public String insertPendingMarginOut(HttpServletRequest request,HttpServletResponse response,@Valid @ModelAttribute("withdrawalModel") InsertMarginOutDto marginOut,BindingResult errors){
		try {
			
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			if(null!=request.getSession().getAttribute("errormessage")){
				request.getSession().removeAttribute("errormessage");
			 }
			if(errors.hasErrors()){
				 List<ObjectError> errorList = errors.getAllErrors();
		            for(ObjectError error : errorList){
		                System.out.println("errormessage:"+error.getDefaultMessage());
		                logger.error("InsertMarginOut error:"+error.getDefaultMessage());
		            }
		            return "withdrawal";
			}
			
        	 JsonUtil jsonUtil = new JsonUtil();
        	 InsertMarginOutRequestDto requestBody = new InsertMarginOutRequestDto();
        	 requestBody.setAccount(ibcode.toString());
        	 requestBody.setCategory(ConstantFields.WITHDRAWAL_CATEGORY);
        	 requestBody.setMethod(ConstantFields.WITHDRAWAL_METHOD);
			 requestBody.setCurrency(marginOut.getCurrency());
			// requestBody.setCurrency(request.getSession().getAttribute(ConstantFields.IB_WITHDRAWAL_CURRENCY).toString());
			 requestBody.setAmount(marginOut.getAmount());
			 requestBody.setTrade_date(new Date());
			String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbWithdrawerHeader(request),requestBody);
        	java.lang.reflect.Type type = new TypeToken<IbResponseDto<InsertMarginOutResponseDto>>() {}.getType();
			logger.info("requestJson: "+requestJson);
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_WITHDRAWAL_INSERT_PATH);
			logger.info("responseJosn: "+responseJson);
			IbResponseDto<InsertMarginOutResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			 BaseResponseHeader ResponseHeader = ibResponseDto.getHeader();
				if(null!=ResponseHeader){
					Map<String, String> map = ResponseHeader.getErrorMap();
					 if((ConstantFields.SUCCESS).equals(ResponseHeader.getStatus())){
					InsertMarginOutResponseDto ibBody=ibResponseDto.getBody();
					logger.info("register status: SUCCESS"+ibBody.getMarginOut());
					//request.setAttribute("withdrawalModel",ibBody.getMarginOut());
					request.getSession().setAttribute("errormessage", "withdrawal.success");
				}else{
					logger.info("InsertMarginOut error");
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
					request.getSession().setAttribute("errormessage", DealErrorsUtil.getErrorInfo(map));
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/margin/getMaxWithdrawal.hyml";
	}
	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}
	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}

	public InsertMarginOutDto  getMarginOutDto(GetMaxWithdrawalResponseDto dto){
		InsertMarginOutDto insertDto= new InsertMarginOutDto();
		insertDto.setIb_code(dto.getIb_code());
		insertDto.setMax_withdrawal(dto.getMax_withdrawal());
		insertDto.setCurrency("USD");
		
		return insertDto;
	}
}
