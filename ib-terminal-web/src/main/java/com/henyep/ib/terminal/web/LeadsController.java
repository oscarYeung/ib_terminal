package com.henyep.ib.terminal.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContext;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.IbLeadBean;
import com.henyep.ib.terminal.api.dto.request.ib.lead.GetIbLeadsRequestDto;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ib.lead.GetIbLeadsResponseDto;
import com.henyep.ib.terminal.api.dto.response.rebate.IbClientRebateResponseDto;
import com.henyep.ib.terminal.operator.MiddleOperator;
import com.henyep.ib.terminal.utils.ConstantFields;
import com.henyep.ib.terminal.utils.DealErrorsUtil;
import com.henyep.ib.terminal.utils.HeaderUtil;
import com.henyep.ib.terminal.utils.JsonUtil;

@Controller
public class LeadsController {
	final static Logger logger = Logger.getLogger(LeadsController.class);
	
	@Resource(name = "middleOperatorBean")
	private MiddleOperator middlewareContext;
	
	
	@RequestMapping(value="/getLeads")
	public String getLeads(HttpServletRequest request,HttpServletResponse response){
		try {
				JsonUtil jsonUtil = new JsonUtil();
				Object ibcode=request.getSession().getAttribute(ConstantFields.IB_CODE);
				GetIbLeadsRequestDto ibBody= new GetIbLeadsRequestDto();
				 ibBody.setIb_code(ibcode.toString());
				 ibBody.setWithSubIbLeads(true);
				// ibBody.setIb_code(1400+"");
				 String requestJson=jsonUtil.toRequestEntityJson(HeaderUtil.getClientLeadsHeader(request),ibBody);
				// GetIbLeadsRequestDto  GetIbLeadsResponseDto
				 logger.info("requestJson: "+requestJson);
				 String respJson =middlewareContext.sendMessage( requestJson,ConstantFields.IB_LEADS_PATH);
				 java.lang.reflect.Type type = new TypeToken<IbResponseDto<GetIbLeadsResponseDto>>() {}.getType();
				 logger.info("responseJosn: "+respJson);
				 IbResponseDto<GetIbLeadsResponseDto> ibResponseDto=jsonUtil.fromJson(respJson, type);
				 BaseResponseHeader responseHeader = ibResponseDto.getHeader();
					if(null!=responseHeader){
						Map<String, String> map = responseHeader.getErrorMap();
						 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
						GetIbLeadsResponseDto leadsDto=ibResponseDto.getBody();
						if(null!=leadsDto){
							List<IbLeadBean> leads=leadsDto.getLeadList();
							request.setAttribute("leads", leads);
							request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
							logger.info("register status: SUCCESS"+leads);
						}
					}else{
						if(ConstantFields.LOGIN.equals(DealErrorsUtil.dealResultError(map, request))){
							 return "redirect:/login.hyml";
						 }
					}
				}
				 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
		request.setAttribute("demoLive", setMapValues(request));
		return "myLead";
	}
	
	public Map<String,String > setMapValues(HttpServletRequest request){
		Map< String, String> map = new HashMap< String, String>();
		RequestContext requestcontext =new RequestContext(request);
		map.put("D", requestcontext.getMessage("ib.demo"));
		map.put("L", requestcontext.getMessage("ib.live"));
		map.put("B", requestcontext.getMessage("ib.both"));
		
		return map;
	}
}

