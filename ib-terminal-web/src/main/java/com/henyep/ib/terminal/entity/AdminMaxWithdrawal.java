package com.henyep.ib.terminal.entity;



public class AdminMaxWithdrawal {

	private Integer account;
	private Double max_withdrawal;
	private String group;
	private String errorInfo;
	private String currency;
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public Double getMax_withdrawal() {
		return max_withdrawal;
	}
	public void setMax_withdrawal(Double max_withdrawal) {
		this.max_withdrawal = max_withdrawal;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}
