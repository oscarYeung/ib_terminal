package com.henyep.ib.terminal.server.dto.marginInOut;

public class MarginInOutDto {

	private String brand_code;
	private String account;
	private String trade_date;
	private Double margin_in;
	private Double margin_out;
	private Double account_balance;
	
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	public Double getMargin_in() {
		return margin_in;
	}
	public void setMargin_in(Double margin_in) {
		this.margin_in = margin_in;
	}
	public Double getMargin_out() {
		return margin_out;
	}
	public void setMargin_out(Double margin_out) {
		this.margin_out = margin_out;
	}
	public Double getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}
	
	
}
