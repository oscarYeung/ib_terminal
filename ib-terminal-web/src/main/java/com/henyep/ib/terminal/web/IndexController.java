package com.henyep.ib.terminal.web;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.request.ibcommission.GetIbSummaryRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbSummaryTrimmedResponseDto;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;
import com.henyep.ib.terminal.utils.TimeUtil;

@Controller
public class IndexController {
	final static Logger logger = Logger.getLogger(IndexController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping("index")
	public String getIndex(HttpServletRequest request,HttpServletResponse response){
		try {
			Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
        	 JsonUtil jsonUtil = new JsonUtil();
        	 GetIbSummaryRequestDto requestBody = new GetIbSummaryRequestDto();
			 requestBody.setIb_code(ibcode.toString());
			 requestBody.setTrade_date(TimeUtil.getShortDate(new Date()));
			
			String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getIbSummaryHeader(request),requestBody);
        	java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbSummaryTrimmedResponseDto>>() {}.getType();
			logger.info("requestJson: "+requestJson);
			
			String responseJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_TRIMMED_SUMMARY_PATH);
			logger.info("responseJosn: "+responseJson);
			IbResponseDto<GetIbSummaryTrimmedResponseDto> ibResponseDto=jsonUtil.fromJson(responseJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					GetIbSummaryTrimmedResponseDto ibBody=ibResponseDto.getBody();
					logger.info("register status: SUCCESS"+ibBody.getIbAccountModel()+" BalanceModel:"+ibBody.getIbMonthBalanceModel()+" ClientAccountModels:"+ibBody.getIbClientAccountModels());
					request.getSession().setAttribute("accountModel",ibBody.getIbAccountModel());
					request.getSession().setAttribute("ClientAccountModels",ibBody.getIbClientAccountModels());
					request.getSession().setAttribute("balanceModel",ibBody.getIbMonthBalanceModel());
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				}else{
					logger.info("index error status:"+responseHeader.getStatus());
					if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
						 return "redirect:/login.hyml";
					 }
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "index";
	}
	
}
