package com.henyep.ib.terminal.api.dto.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class BaseIbCodeRequestBodyDto extends BaseRequestBodyDto implements Serializable {

	private static final long serialVersionUID = -5102975696239658369L;
	@NotNull(message = Constants.ERR_COMMON_IB_CODE_IS_BLANK)
	protected String ib_code;	

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

}
