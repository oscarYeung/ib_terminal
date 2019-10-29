package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.UserRolesBean;

public interface UserRolesDao{
	public void saveUserRoles(final UserRolesBean userRoles) throws Exception;

	public List<UserRolesBean> getAllUserRoless() throws Exception;

	public List<UserRolesBean> getUserRolesByKey(String user_code, Integer role_id) throws Exception;

	public int updateUserRoles(UserRolesBean userRoles) throws Exception;

	public int deleteUserRoles(String user_code, Integer role_id) throws Exception;
	
	public Integer getUserRolesByUserCode(String user_code) throws Exception;
}
