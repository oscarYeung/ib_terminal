package com.henyep.ib.terminal.server.service;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.PermissionBean;

public interface UserPermissionService {

	public List<PermissionBean> getPermissionListByActiveUser(String user_code);
}
