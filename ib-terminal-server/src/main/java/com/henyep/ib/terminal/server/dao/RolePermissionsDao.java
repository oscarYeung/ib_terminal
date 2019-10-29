package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.RolePermissionsBean;

public interface RolePermissionsDao{
	public void saveRolePermissions(final RolePermissionsBean rolePermissions) throws Exception;

	public List<RolePermissionsBean> getAllRolePermissionss() throws Exception;

	public List<RolePermissionsBean> getRolePermissionsByKey(Integer role_id, Integer permission_id) throws Exception;

	public int updateRolePermissions(RolePermissionsBean rolePermissions) throws Exception;

	public int deleteRolePermissions(Integer role_id, Integer permission_id) throws Exception;
}
