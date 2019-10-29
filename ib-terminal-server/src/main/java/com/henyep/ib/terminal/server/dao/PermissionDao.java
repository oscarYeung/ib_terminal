package com.henyep.ib.terminal.server.dao;

import java.util.List;

import com.henyep.ib.terminal.api.dto.db.PermissionBean;

public interface PermissionDao {
	public int savePermission(final PermissionBean permission) throws Exception;

	public List<PermissionBean> getAllPermissions() throws Exception;

	public List<PermissionBean> getPermissionByKey(Integer id) throws Exception;

	public int updatePermission(PermissionBean permission) throws Exception;

	public int deletePermission(Integer id) throws Exception;

	public List<PermissionBean> getPermissionListByActiveUser(String user_code) throws Exception;
}
