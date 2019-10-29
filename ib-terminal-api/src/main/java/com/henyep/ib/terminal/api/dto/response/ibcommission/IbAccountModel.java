package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;

public class IbAccountModel implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2479372101704169246L;
	
	private String ib_code;
	private Double account_balance;
	private String currency;
	
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
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