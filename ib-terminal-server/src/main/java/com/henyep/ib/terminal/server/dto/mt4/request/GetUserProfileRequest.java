package com.henyep.ib.terminal.server.dto.mt4.request;

import com.google.gson.annotations.SerializedName;

public class GetUserProfileRequest extends BaseRequestModel{

	@SerializedName("Login")
	private Integer login;

	public Integer getLogin() {
		return login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}
}
