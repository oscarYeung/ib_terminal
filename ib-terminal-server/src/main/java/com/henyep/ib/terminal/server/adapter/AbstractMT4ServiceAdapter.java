package com.henyep.ib.terminal.server.adapter;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;

import com.henyep.ib.terminal.server.dto.mt4.request.BaseRequestModel;
import com.henyep.ib.terminal.server.dto.mt4.request.MT4LoginRequest;
import com.henyep.ib.terminal.server.dto.mt4.response.BaseResponseModel;
import com.henyep.ib.terminal.server.dto.mt4.response.MT4LoginResponse;
import com.henyep.ib.terminal.server.global.Constants;
import com.henyep.ib.terminal.server.util.HttpClientUtil;
import com.henyep.white.label.api.dto.db.custom.CompanyRegistrationProfile;
import com.henyep.white.label.api.dto.request.BaseRequestBodyDto;

public abstract class AbstractMT4ServiceAdapter<T extends BaseRequestBodyDto, S extends BaseResponseModel> implements MT4ServiceAdapter {

	protected final transient Log logger = LogFactory.getLog(getClass());

	@Resource(name = "HttpClientUtil")
	protected HttpClientUtil httpClientUtil;

	protected Class<T> reqClass;
	protected Class<S> resClass;

	public AbstractMT4ServiceAdapter(Class<T> reqClass, Class<S> resClass) {
		this.reqClass = reqClass;
		this.resClass = resClass;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getSupportedModelClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public String sendRequest(String ip, String port, String mt4login, String mt4Password, String webServiceUrl, BaseRequestBodyDto data) {
		if (this.reqClass.isInstance(data))
			return sendRequestToMT4(ip, port, mt4login, mt4Password, webServiceUrl, (T) data);
		else
			logger.error("Class mismatch");

		return null;
	}

	@Override
	public Object convertToObject(String mt4Response) {
		return httpClientUtil.convertToObject(mt4Response, resClass);
	}

	protected String getToken(String endPoint, String mt4ServerHost, Integer login, String password) {
		try {
			MT4LoginRequest loginRequest = new MT4LoginRequest();
			loginRequest.setHost(mt4ServerHost);
			loginRequest.setLogin(login);
			loginRequest.setPassword(password);
			String mt4Response = httpClientUtil.sendRestfulPostRequest(endPoint, loginRequest);
			MT4LoginResponse loginResponse = httpClientUtil.convertToObject(mt4Response, MT4LoginResponse.class);
			if (loginResponse.isSuccess())
				return loginResponse.getToken();
		} catch (Exception e) {
			logger.error(e, e);
		}
		return null;

	}

	protected String sendRequestToMT4(String ip, String port, String mt4login, String mt4Password, String webServiceUrl, T data) {
		String response = null;
		try {
			// Login
			String mt4ServerHost = ip + ":" + port;
			
			logger.info(mt4ServerHost);
			
			Integer login = Integer.parseInt(mt4login);
			//String url = "http://localhost:8101/mt4/service/rest/authservice/login";
			String url =  webServiceUrl + Constants.MT4_WEB_SERVICE_LOGIN_LINK;
			
			logger.info("get token, url: " + url);
			String token = this.getToken(url, mt4ServerHost, login, mt4Password);
			logger.info("token: " + token);
			
			if (StringUtils.isNotBlank(token)) {
				// Get Request Model
				BaseRequestModel requestModel = getMT4RequestModel(token, data);
				requestModel.setToken(token);
				url = webServiceUrl + getServiceLink();
				logger.info("send restful request: " + url);
				response = this.httpClientUtil.sendRestfulPostRequest(url, requestModel);
				logger.info("response: " + response);
			}
		} catch (ClientProtocolException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}

		return response;
	}

	protected abstract BaseRequestModel getMT4RequestModel(String token, T data);
	
	protected abstract String getServiceLink();

}