package com.henyep.ib.terminal.api.dto.request.ib.client.map;

import java.io.Serializable;

public class InsertFromWlRegistrationRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9194130492291010780L;
	
	
	private String first_name;
	private String last_name;
	
	private String gender;
	private String email;
	private String phone;
	
	
	private String client_package;
	private String client_code;
	private String ib_code;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getClient_package() {
		return client_package;
	}
	public void setClient_package(String client_package) {
		this.client_package = client_package;
	}
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	
	

}
