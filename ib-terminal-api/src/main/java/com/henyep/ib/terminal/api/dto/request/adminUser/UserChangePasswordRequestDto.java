package com.henyep.ib.terminal.api.dto.request.adminUser;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.henyep.ib.terminal.api.global.Constants;

public class UserChangePasswordRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209302392278585855L;
	
	@NotBlank(message = Constants.ERR_USER_CODE_BLANK)
	@NotNull(message = Constants.ERR_USER_CODE_BLANK)
	private String userCode;
	
	@NotBlank(message = Constants.ERR_USER_NEW_PASSWORD_BLANK)
	@NotNull(message = Constants.ERR_USER_NEW_PASSWORD_BLANK)
	private String newPassword;
	
	@NotBlank(message = Constants.ERR_USER_OLD_PASSWORD_BLANK)
	@NotNull(message = Constants.ERR_USER_OLD_PASSWORD_BLANK)
	private String oldPassword;
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	
}
