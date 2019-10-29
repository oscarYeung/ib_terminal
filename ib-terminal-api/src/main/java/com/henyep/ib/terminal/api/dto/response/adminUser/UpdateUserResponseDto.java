package com.henyep.ib.terminal.api.dto.response.adminUser;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.UserBean;

public class UpdateUserResponseDto implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8807262986870277991L;

	
	private UserBean userBean;


	public UserBean getUserBean() {
		return userBean;
	}


	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
}
