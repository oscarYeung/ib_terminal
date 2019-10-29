package com.henyep.ib.terminal.api.dto.request.adminUser;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.api.global.Constants;

public class UpdateUserRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -834344015255594713L;
	
	
	@Valid
	@NotNull(message = Constants.ERR_USER_NULL)
	private UserBean updateUserBean;



	public UserBean getUpdateUserBean() {
		return updateUserBean;
	}

	public void setUpdateUserBean(UserBean updateUserBean) {
		this.updateUserBean = updateUserBean;
	}
	
}
