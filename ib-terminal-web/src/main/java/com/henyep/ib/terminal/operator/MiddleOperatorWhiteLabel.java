package com.henyep.ib.terminal.operator;

import java.net.URLEncoder;

import org.apache.camel.CamelContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.henyep.ib.terminal.entity.ConfigFields;
import com.henyep.ib.terminal.utils.SpringContextUtil;

public class MiddleOperatorWhiteLabel {

	private CamelContext camelContext;
	private ConfigFields configFields;
	/**
	 * use camel route to send message
	 * @param json
	 * @param serverpath
	 * @return
	 */
	public String sendMessage(String json,String serverpath){
				
//		Endpoint point = camelContext.getEndpoint("whiteLabelServerApiEntrance");
//		ProducerTemplate middlewareTemplate=camelContext.createProducerTemplate();
//		point.getEndpointConfiguration().setParameter("requestTimeout", ConstantFields.SERVER_TIMEOUT);
//		Map<String, Object> headers = new HashMap<String, Object>();
//		//headers.put("serverApiName", ConstantFields.SERVERAPI_NAME);
//		headers.put("serverApiPath", serverpath);
//		
//		String respJson=middlewareTemplate.requestBodyAndHeaders(point, json, headers, String.class);
//		return respJson;
		
		String resposneJson="";
		try {
			 HttpClient httpClient = HttpClientBuilder.create().build();
			 String requestUrl=configFields.getWhite_label_request_url();
			  //logger.info("request server url:"+requestUrl+requestUrl+serverpath);
			  //logger.info("request Json:"+json);
			  HttpPost request = new HttpPost(requestUrl+serverpath);
			  StringEntity entity;
			  entity = new StringEntity(json);
			  request.addHeader("Content-type", "application/json");
			  request.setEntity(entity);
			  HttpResponse response = httpClient.execute(request);
			  resposneJson= EntityUtils.toString(response.getEntity(), "UTF-8");
			  //logger.info("resposneJson :"+resposneJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resposneJson	;
	}
	
	
	
	public CamelContext getCamelContext() {
		return camelContext;
	}

	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}
	
	public ConfigFields getConfigFields() {
		return configFields;
	}
	public void setConfigFields(ConfigFields configFields) {
		this.configFields = configFields;
	}
	
}
