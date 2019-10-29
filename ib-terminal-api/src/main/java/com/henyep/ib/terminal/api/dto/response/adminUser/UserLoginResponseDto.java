package com.henyep.ib.terminal.api.dto.response.adminUser;

import java.io.Serializable;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.UserBean;

public class UserLoginResponseDto implements Serializable {

	private static final long serialVersionUID = -412497101852875623L;

	private UserBean userBean;

	private List<Integer> permissionList;

	public List<Integer> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Integer> permissionList) {
		this.permissionList = permissionList;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
