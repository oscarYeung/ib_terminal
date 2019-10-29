package com.henyep.ib.terminal.api.dto.request.adminUser;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.api.global.Constants;

public class AddUserRequestDto implements Serializable {

	private static final long serialVersionUID = -4966025992031155000L;

	@NotNull(message = Constants.ERR_USER_NULL)
	@Valid
	private UserBean newUser;

	public UserBean getNewUser() {
		return newUser;
	}

	public void setNewUser(UserBean newUser) {
		this.newUser = newUser;
	}

}
