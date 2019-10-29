package com.henyep.ib.terminal.api.dto.response.ibcommission;

public class TradeAccountModel {

	private String account;
	private Double account_balance;
	private String currency;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Double getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
