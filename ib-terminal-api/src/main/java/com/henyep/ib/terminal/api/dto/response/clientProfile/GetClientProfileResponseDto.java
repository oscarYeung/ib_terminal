package com.henyep.ib.terminal.api.dto.response.clientProfile;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;

public class GetClientProfileResponseDto implements Serializable{

	private static final long serialVersionUID = 5552470105753834967L;
	
	private ClientDetailsBean client_details;
	
	private List<ClientMarginModel> client_margins;


	public ClientDetailsBean getClient_details() {
		return client_details;
	}

	public void setClient_details(ClientDetailsBean client_details) {
		this.client_details = client_details;
	}

	public List<ClientMarginModel> getClient_margins() {
		return client_margins;
	}

	public void setClient_margins(List<ClientMarginModel> client_margins) {
		this.client_margins = client_margins;
	}

	
	
	
}
