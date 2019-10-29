package com.henyep.ib.terminal.utils;

import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.http.protocol.RequestContent;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.support.RequestContext;

import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.response.BaseResponseHeader;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;
import com.henyep.ib.terminal.api.dto.response.ibcommission.GetIbClientSummaryResponseDto;
import com.henyep.ib.terminal.web.RegController;

public class DealErrorsUtil {
	final static Logger logger = Logger.getLogger(DealErrorsUtil.class);
	
	public static String getErrorInfo(Map<String, String> map){
		String errorInfo="";
		for(Object obj : map.keySet()){
			Object Objectvalue = map.get(obj );
			if(null!=Objectvalue&&!"".equals(Objectvalue)){
				errorInfo=Objectvalue.toString();
			}
			logger.error("error key"+obj+" value:"+Objectvalue);
			break;
		}
		return errorInfo.trim() ;
	}
	public static String getInterErrorInfo(Map<String, String> map,HttpServletRequest request){
		String errorInfo="";
		//common.secret.key.null
		RequestContext requestContent = new RequestContext(request);
		for(Object obj : map.keySet()){
			Object Objectvalue = map.get(obj );
			if(null!=Objectvalue&&!"".equals(Objectvalue)){
				errorInfo=obj+","+requestContent.getMessage(Objectvalue.toString());
			}
			logger.error("getInterErrorInfo"+errorInfo);
			break;
		}
		return errorInfo ;
	}
	public <T>  String DealResultForAjax(String responseJson,HttpServletRequest request){
		
		Type type = new TypeToken<IbResponseDto<T>>() {}.getType();
		JsonUtil jsonUtil = new JsonUtil();
		IbResponseDto<T> ibResponseDto=jsonUtil.fromJson(responseJson, type);
		String reslut=null;
		BaseResponseHeader responseHeader = ibResponseDto.getHeader();
		if(null!=responseHeader){
			Map<String, String> map = responseHeader.getErrorMap();
			 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				reslut=responseHeader.getStatus();
				if(null!=responseHeader.getSecretKey()&&!"".equals(responseHeader.getSecretKey())){
					request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
				}else{
					logger.info("secretKey key is null");
				}
				
			}else{
				reslut=DealErrorsUtil.getInterErrorInfo(map,request);
				request.setAttribute("errormessage", reslut);
			}
		}
		return reslut;
	}
//// IbResponseDto<IbProfileGetResp> ibResponseDto= dealUtil.<IbProfileGetResp>DealResult(responseJson, request);	
public <T>  IbResponseDto<T>  DealResultError(String responseJson,HttpServletRequest request){
		Type type = new TypeToken<IbResponseDto<T>>() {}.getType();
		JsonUtil jsonUtil = new JsonUtil();
		IbResponseDto<T> ibResponseDto=jsonUtil.fromJson(responseJson, type);
		String reslut=null;
		BaseResponseHeader responseHeader = ibResponseDto.getHeader();
		if(null!=responseHeader){
			Map<String, String> map = responseHeader.getErrorMap();
			 if((ConstantFields.SUCCESS).equals(responseHeader.getStatus())){
				reslut=responseHeader.getStatus();
				request.getSession().setAttribute(ConstantFields.IB_SECRET_KEY, responseHeader.getSecretKey());
			}else{
				if(map.containsKey(ConstantFields.ERROR_10015)||map.containsKey(ConstantFields.ERROR_10033)){
					ibResponseDto=null;
				}
				reslut=getErrorInfo(map);
				request.setAttribute("errormessage", getErrorInfo(map));
			}
		}
		return ibResponseDto;
	}



	public static String dealResultError(Map<String, String> map,HttpServletRequest request){
		String result=null;
		if(map.containsKey(ConstantFields.ERROR_10015)||map.containsKey(ConstantFields.ERROR_10033)){
			result=ConstantFields.LOGIN;
		}
		request.setAttribute("errormessage", getErrorInfo(map));
		return result;
	}
	
}