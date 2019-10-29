package com.henyep.ib.terminal.api.dto.response.password;

import java.io.Serializable;

public class VerifyTokenResponseDto implements Serializable {

	private static final long serialVersionUID = 7688306325236238013L;
	private String ib_Code;
	private String user_type;
	private Boolean success;

	public String getIb_Code() {
		return ib_Code;
	}

	public void setIb_Code(String ib_Code) {
		this.ib_Code = ib_Code;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
