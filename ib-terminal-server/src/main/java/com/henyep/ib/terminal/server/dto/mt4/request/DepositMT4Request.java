package com.henyep.ib.terminal.server.dto.mt4.request;

import com.google.gson.annotations.SerializedName;

public class DepositMT4Request extends BaseRequestModel {
	@SerializedName("Login")
	private Integer login;
	@SerializedName("Comment")
	private String comment;
	@SerializedName("Amount")
	private Double amount;

	public Integer getLogin() {
		return login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
