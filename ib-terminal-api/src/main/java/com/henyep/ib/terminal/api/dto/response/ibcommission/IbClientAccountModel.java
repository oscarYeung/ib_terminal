package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;

public class IbClientAccountModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4136047922930492662L;
	
	private String client_code;
	private Double balance;
	private String trading_platform;
	private String currency;
	
	
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getTrading_platform() {
		return trading_platform;
	}
	public void setTrading_platform(String trading_platform) {
		this.trading_platform = trading_platform;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
