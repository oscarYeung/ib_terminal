package com.henyep.ib.terminal.api.dto.request.marginout;

public class MaxWithdrawalModel {

	private String ib_code;
	private Double pending_margin;
	private Double account_balance;
	
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public Double getPending_margin() {
		return pending_margin;
	}
	public void setPending_margin(Double pending_margin) {
		this.pending_margin = pending_margin;
	}
	public Double getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}
	
	
}
