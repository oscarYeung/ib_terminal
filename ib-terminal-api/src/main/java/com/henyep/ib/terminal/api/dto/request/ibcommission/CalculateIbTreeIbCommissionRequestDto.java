package com.henyep.ib.terminal.api.dto.request.ibcommission;

import java.util.List;

import com.henyep.ib.terminal.api.dto.request.BaseDateTimeRequestBodyDto;

public class CalculateIbTreeIbCommissionRequestDto extends BaseDateTimeRequestBodyDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -191154480080726506L;
	
	private List<String> head_ib_codes;

	public List<String> getHead_ib_codes() {
		return head_ib_codes;
	}

	public void setHead_ib_codes(List<String> head_ib_codes) {
		this.head_ib_codes = head_ib_codes;
	}
	
}
