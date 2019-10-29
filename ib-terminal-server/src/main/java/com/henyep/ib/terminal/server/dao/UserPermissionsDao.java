package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.UserPermissionsBean;

public interface UserPermissionsDao{
	public void saveUserPermissions(final UserPermissionsBean userPermissions) throws Exception;

	public List<UserPermissionsBean> getAllUserPermissionss() throws Exception;

	public List<UserPermissionsBean> getUserPermissionsByKey(String user_code, Integer permission_id) throws Exception;

	public int updateUserPermissions(UserPermissionsBean userPermissions) throws Exception;

	public int deleteUserPermissions(String user_code, Integer permission_id) throws Exception;
}
