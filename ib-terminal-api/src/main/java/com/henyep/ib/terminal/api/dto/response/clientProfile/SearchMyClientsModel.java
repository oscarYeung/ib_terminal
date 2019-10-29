package com.henyep.ib.terminal.api.dto.response.clientProfile;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class SearchMyClientsModel {

	private String trading_id;
	private String ib_code;
	private String client_name;
	private String phone_no;
	private String email;
	private Double balance;
	private Double equity;
	private String currency;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date join_date;
	public String getTrading_id() {
		return trading_id;
	}
	public void setTrading_id(String trading_id) {
		this.trading_id = trading_id;
	}
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getJoin_date() {
		return join_date;
	}
	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}

	public String getClient_name(){
		return client_name;
	}
	
	public void setClient_name(String client_name){
		this.client_name = client_name;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getEquity() {
		return equity;
	}
	public void setEquity(Double equity) {
		this.equity = equity;
	}
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
