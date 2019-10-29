package com.henyep.ib.terminal.server.dto.report;

public class OwnerIbDto {
	private String user_code;
	private String ib_code;
	private Integer ib_min_id;
	private Integer ib_max_id;
	private String email;
	
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public Integer getIb_min_id() {
		return ib_min_id;
	}
	public void setIb_min_id(Integer ib_min_id) {
		this.ib_min_id = ib_min_id;
	}
	public Integer getIb_max_id() {
		return ib_max_id;
	}
	public void setIb_max_id(Integer ib_max_id) {
		this.ib_max_id = ib_max_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
