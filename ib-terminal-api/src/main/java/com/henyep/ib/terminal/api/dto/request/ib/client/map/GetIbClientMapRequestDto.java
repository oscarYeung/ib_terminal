package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import com.henyep.ib.terminal.api.dto.request.BaseTradeDateRequestBodyDto;

public class GetIbClientMapRequestDto extends BaseTradeDateRequestBodyDto {

	private static final long serialVersionUID = 6456425519625845990L;
	private String ib_code;

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

}
