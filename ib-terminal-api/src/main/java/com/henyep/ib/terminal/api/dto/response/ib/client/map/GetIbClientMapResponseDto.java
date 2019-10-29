package com.henyep.ib.terminal.api.dto.response.ib.client.map;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;

public class GetIbClientMapResponseDto implements Serializable {

	private static final long serialVersionUID = -959111875378362865L;
	private List<IbClientMapBean> data;

	public List<IbClientMapBean> getData() {
		return data;
	}

	public void setData(List<IbClientMapBean> data) {
		this.data = data;
	}

}
