package com.henyep.ib.terminal.api.dto.request.adminUser;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.henyep.ib.terminal.api.global.Constants;

public class UserLoginRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5304769087721324931L;
	
	@NotBlank(message = Constants.ERR_USER_CODE_BLANK)
	@NotNull(message = Constants.ERR_USER_CODE_BLANK)
	private String userCode;
	
	@NotBlank(message = Constants.ERR_USER_PASSWORD_BLANK)
	@NotNull(message = Constants.ERR_USER_PASSWORD_BLANK)
	private String password;



	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
