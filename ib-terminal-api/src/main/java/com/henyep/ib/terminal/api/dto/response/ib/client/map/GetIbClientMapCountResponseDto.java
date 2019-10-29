package com.henyep.ib.terminal.api.dto.response.ib.client.map;

import java.io.Serializable;

public class GetIbClientMapCountResponseDto implements Serializable {

	private static final long serialVersionUID = 7771168771232590517L;

	public Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
