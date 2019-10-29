package com.henyep.ib.terminal.api.dto.response.adminUser;

import java.io.Serializable;

public class ResetSystemCacheResponseDto implements Serializable {

	private static final long serialVersionUID = 3299416963662514891L;
	private String type;

	private boolean success;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
