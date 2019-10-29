package com.henyep.ib.terminal.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.communal.ConstantDefineCode;
import com.henyep.ib.terminal.api.dto.request.rebate.IbRebateRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateWithDetails;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;

@RequestMapping(value="/ib")
@Controller
public class IbRebateController {
	final static Logger logger = Logger.getLogger(IbRebateController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/rebate")
	public String showIbRate(HttpServletRequest request,HttpServletResponse response){
		try {
				JsonUtil jsonUtil = new JsonUtil();
				Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
				 IbRebateRequestDto ibBody= new IbRebateRequestDto();
				 ibBody.setBrand_code(ConstantDefineCode.BRAND_CODE_CN);
				 ibBody.setIb_code(ibcode.toString());
				// ibBody.setIb_code("10071766");
				 ibBody.setStart_date(new Date());
			 
				 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getRebateHeader(request),ibBody);
				 
				 logger.info("requestJson: "+requestJson);
				 String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_RABATE_PATH);
				 java.lang.reflect.Type type = new TypeToken<IbResponseDto<IbClientRebateResponseDto>>() {}.getType();
				 logger.info("responseJosn: "+respJson);
				 IbResponseDto<IbClientRebateResponseDto> ibResponseDto=jsonUtil.fromJson(respJson, type);
				 BaseResponseHeader responseHeader = ibResponseDto.getHeader();
					if(null!=responseHeader){
						Map<String, String> map = responseHeader.getErrorMap();
						 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						IbClientRebateResponseDto rebateDto=ibResponseDto.getBody();
						if(null!=rebateDto){
							List<IbClientRebateWithDetails> rebates=rebateDto.getIbClientRebateList();
							request.setAttribute("rebates", rebates);
							 request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY,responseHeader.getSecretKey());
							logger.info("register status: SUCCESS"+rebates);
						}
						logger.info("response body rebateDto:"+rebateDto);
					}else{
						if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
							 return "redirect:/login.hyml";
						 }
					}
				}
				 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return "rebate";
	}
	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}
	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}
}
