package com.camel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.henyep.ib.terminal.utils.ConstantFields;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:camel*.xml" })
public class TestHttp {
	
	/*@Resource(name = "middlewareTemplate")
	ProducerTemplate middlewareTemplate;*/
	
	@Resource(name = "middlewareContext")
	private CamelContext camelContext;
	@Resource(name = "serverApiEntrance")
	private Endpoint point;
  	public Endpoint getPoint() {
		return point;
	}
	public void setPoint(Endpoint point) {
		this.point = point;
	}
	
	
	@Test
	public void test1(){
		
		//ProducerTemplate middlewareTemplate;
		//Endpoint point = camelContext.getEndpoint("serverApiEntrance");
		/*ProducerTemplate middlewareTemplate=camelContext.createProducerTemplate();
		String json="";
		point.getEndpointConfiguration().setParameter("requestTimeout", ConstantFields.SERVER_TIMEOUT);
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("serverApiName", ConstantFields.SERVERAPI_NAME);
		headers.put("serverApiPath", "IbTerminalWeb/login.hyml");
		String respJson=middlewareTemplate.requestBodyAndHeaders(point, json, headers, String.class);
		System.out.println(respJson);*/
	}
	public CamelContext getCamelContext() {
		return camelContext;
	}
	public void setCamelContext(CamelContext camelContext) {
		this.camelContext = camelContext;
	}
	/*public ProducerTemplate getMiddlewareTemplate() {
		return middlewareTemplate;
	}
	public void setMiddlewareTemplate(ProducerTemplate middlewareTemplate) {
		this.middlewareTemplate = middlewareTemplate;
	}*/
  	
}
