package com.henyep.ib.terminal.api.dto.response.product.symbol;

import java.io.Serializable;
import java.util.List;

public class GetCurrentProductGroupsResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805131544981121641L;

	private List<SpreadTypeProductGroupDto> spreadTypeProductGroupList;

	public List<SpreadTypeProductGroupDto> getSpreadTypeProductGroupList() {
		return spreadTypeProductGroupList;
	}

	public void setSpreadTypeProductGroupList(List<SpreadTypeProductGroupDto> spreadTypeProductGroupList) {
		this.spreadTypeProductGroupList = spreadTypeProductGroupList;
	}
	
}
