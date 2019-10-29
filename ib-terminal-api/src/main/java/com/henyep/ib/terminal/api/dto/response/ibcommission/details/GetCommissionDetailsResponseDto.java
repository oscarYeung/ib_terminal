package com.henyep.ib.terminal.api.dto.response.ibcommission.details;

import java.io.Serializable;
import java.util.List;

public class GetCommissionDetailsResponseDto implements Serializable {

	private static final long serialVersionUID = 2256158131619778324L;

	private List<IbCommissionDetailsWebModel> list;

	public List<IbCommissionDetailsWebModel> getList() {
		return list;
	}

	public void setList(List<IbCommissionDetailsWebModel> list) {
		this.list = list;
	}

}