package com.henyep.ib.terminal.server.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.henyep.ib.terminal.server.util.adapter.JsonDateTypeAdapter;
import com.henyep.ib.terminal.server.dto.mt4.response.BaseResponseModel;

@Component(value = "HttpClientUtil")
public class HttpClientUtil {
	private final transient Log logger = LogFactory.getLog(getClass());
	private Gson g;

	public HttpClientUtil() {
		g = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDateTypeAdapter()).create();
	}
	
	public <T extends BaseResponseModel> T convertToObject(String json, Class<T> type) {
		return g.fromJson(json, type);
	}
	

	public <T> T convertToResponseObj(String responseString, Type type) {
		T rtn = g.fromJson(responseString, type);
		return rtn;
	}

	public String sendRestfulPostRequest(String endPoint, Object data) throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(endPoint);
		String content = g.toJson(data);
		logger.info("SEND : " + content);
		StringEntity entity = new StringEntity(content);
		request.addHeader("Content-type", "application/json");

		request.setEntity(entity);
		HttpResponse response = httpClient.execute(request);
		return EntityUtils.toString(response.getEntity(), "UTF-8");
	}
}