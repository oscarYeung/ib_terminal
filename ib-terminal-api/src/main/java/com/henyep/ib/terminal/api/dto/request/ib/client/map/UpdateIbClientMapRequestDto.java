package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class UpdateIbClientMapRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3354987856325079478L;
	
	private IbClientMapBean ib_client_map;

	public IbClientMapBean getIb_client_map() {
		return ib_client_map;
	}

	public void setIb_client_map(IbClientMapBean ib_client_map) {
		this.ib_client_map = ib_client_map;
	}

}
