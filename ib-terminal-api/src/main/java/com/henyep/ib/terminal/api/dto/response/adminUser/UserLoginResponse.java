package com.henyep.ib.terminal.api.dto.response.adminUser;

import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.api.dto.response.IbResponseDto;

public class UserLoginResponse extends IbResponseDto<UserLoginResponseDto>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3325352307166539048L;
	
	
	private UserBean userBean;


	public UserBean getUserBean() {
		return userBean;
	}


	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
