package com.henyep.ib.terminal.api.dto.request.ib;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.henyep.ib.terminal.api.global.Constants;


public class IbLoginRequestDto {
	@NotBlank(message = Constants.ERR_IB_LOGIN_NAME_IS_BLANK)
	private String ibLoginName;
	
	@NotBlank(message = Constants.ERR_IB_LOGIN_PASSWORD_IS_BLANK)
	private String ibLoginPassWord;
	public String getIbLoginName() {
		return ibLoginName;
	}
	public void setIbLoginName(String ibLoginName) {
		this.ibLoginName = ibLoginName;
	}
	public String getIbLoginPassWord() {
		return ibLoginPassWord;
	}
	public void setIbLoginPassWord(String ibLoginPassWord) {
		this.ibLoginPassWord = ibLoginPassWord;
	}
}