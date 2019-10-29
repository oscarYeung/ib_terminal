package com.henyep.ib.terminal.api.dto.response.adminUser;

import java.io.Serializable;

import com.henyep.ib.terminal.api.dto.db.UserBean;

public class AddUserResponseDto implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3137723522571415564L;

	private UserBean AddedUserBean;

	public UserBean getAddedUserBean() {
		return AddedUserBean;
	}

	public void setAddedUserBean(UserBean addedUserBean) {
		AddedUserBean = addedUserBean;
	}
}
