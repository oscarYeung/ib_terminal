package com.henyep.ib.terminal.entity;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordDto {


	@NotEmpty(message = "passowrd.oldpassword.not.blank")
	private String oldPassword;
	@NotEmpty(message = "passowrd.newPassword.not.blank")
	private String newPassword;
	@NotEmpty(message = "passowrd.repassword.not.blank")
	private String renewPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getRenewPassword() {
		return renewPassword;
	}
	public void setRenewPassword(String renewPassword) {
		this.renewPassword = renewPassword;
	}
	
	

}
