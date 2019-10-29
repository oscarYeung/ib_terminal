package com.henyep.ib.terminal.api.dto.request.adminUser;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.global.Constants;

public class ResetSystemCacheRequestDto implements Serializable {
	
	private static final long serialVersionUID = 7671624224009156150L;
	@NotNull(message = Constants.ERR_COMMON_TYPE_IS_NULL)
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
