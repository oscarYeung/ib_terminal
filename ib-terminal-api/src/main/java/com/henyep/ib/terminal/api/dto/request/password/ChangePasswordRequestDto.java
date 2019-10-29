package com.henyep.ib.terminal.api.dto.request.password;

import java.io.Serializable;

public class ChangePasswordRequestDto implements Serializable {

	private static final long serialVersionUID = -7931512241106737967L;
	private String user_code;
	private String user_type;
	private String old_password;
	private String new_password;

	public String getUser_code() {
		return user_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getOld_password() {
		return old_password;
	}

	public void setOld_password(String old_password) {
		this.old_password = old_password;
	}

	public String getNew_password() {
		return new_password;
	}

	public void setNew_password(String new_password) {
		this.new_password = new_password;
	}

}
