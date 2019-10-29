package com.henyep.ib.terminal.server.dao;
import java.util.List;
import java.util.Date;
import com.henyep.ib.terminal.api.dto.db.RoleBean;

public interface RoleDao{
	public void saveRole(final RoleBean role) throws Exception;

	public List<RoleBean> getAllRoles() throws Exception;

	public List<RoleBean> getRoleByKey(Integer id) throws Exception;

	public int updateRole(RoleBean role) throws Exception;

	public int deleteRole(Integer id) throws Exception;
}
