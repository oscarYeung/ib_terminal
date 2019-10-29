package com.henyep.ib.terminal.server.adapter;

import com.henyep.white.label.api.dto.request.BaseRequestBodyDto;

public interface MT4ServiceAdapter {

	public String sendRequest(String ip, String port, String mt4login, String mt4Password, String webServiceUrl, BaseRequestBodyDto data);
	
	public Object convertToObject(String mt4Response);

}

