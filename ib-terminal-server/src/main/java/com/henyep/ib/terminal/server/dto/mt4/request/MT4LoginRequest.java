package com.henyep.ib.terminal.server.dto.mt4.request;


import com.google.gson.annotations.SerializedName;

public class MT4LoginRequest {

	@SerializedName("Host")
	private String host;
	@SerializedName("Login")
	private Integer login;
	@SerializedName("Password")
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getLogin() {
		return login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}