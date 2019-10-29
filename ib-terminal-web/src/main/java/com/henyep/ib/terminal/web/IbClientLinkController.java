package com.henyep.ib.terminal.web;


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
import com.henyep.ib.terminal.api.dto.db.IbClientLinkBean;
import com.henyep.ib.terminal.api.dto.request.BaseIbCodeRequestBodyDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.client.link.GetIbClientLinkResponseDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;

@RequestMapping(value="/ib")
@Controller
public class IbClientLinkController {

	final static Logger logger = Logger.getLogger(IbClientLinkController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	@RequestMapping(value="/getClientLinks")
	public String getClientLinks(HttpServletRequest request,HttpServletResponse response){
		try {
			 Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
			 JsonUtil jsonUtil = new JsonUtil();
			 BaseIbCodeRequestBodyDto ibBody= new BaseIbCodeRequestBodyDto();
			 ibBody.setBrand_code(ConstantDefineCode.BRAND_CODE_CN);
			 ibBody.setIb_code(ibcode.toString());
			 
			 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientLinkHeader(request),ibBody);
			logger.info("requestJson: "+requestJson);
			String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.CLIENT_LINK_PATH);
			java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbClientLinkResponseDto>>() {}.getType();
			
			logger.info("responseJosn: "+respJson);
			IbResponseDto<GetIbClientLinkResponseDto> ibResponseDto=jsonUtil.fromJson(respJson, type);
			BaseResponseHeader responseHeader = ibResponseDto.getHeader();
			if(null!=responseHeader){
				Map<String, String> map = responseHeader.getErrorMap();
				 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
					GetIbClientLinkResponseDto ibclient=ibResponseDto.getBody();
					List<IbClientLinkBean> links=ibclient.getIbClientLinkList();
					request.setAttribute("links", links);
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
					logger.info("register status: SUCCESS"+ibclient.getIbClientLinkList());
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
		
		return "ibclientlink";
	}
	public MiddleOperator getMiddlewareContext() {
		return middlewareContext;
	}
	public void setMiddlewareContext(MiddleOperator middlewareContext) {
		this.middlewareContext = middlewareContext;
	}

}
