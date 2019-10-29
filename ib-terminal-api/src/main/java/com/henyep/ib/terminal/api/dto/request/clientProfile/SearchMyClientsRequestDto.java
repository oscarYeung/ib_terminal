package com.henyep.ib.terminal.api.dto.request.clientProfile;

import java.io.Serializable;

public class SearchMyClientsRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5926940830032618914L;

	// optional, NULL means all
	private String trading_id;
	// optional, NULL means all
	private String ib_code;
	private String name;
	private String phone;
	private String email;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	
	
	
	
}
