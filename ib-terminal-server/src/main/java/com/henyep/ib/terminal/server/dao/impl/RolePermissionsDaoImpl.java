package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.RolePermissionsBean;
import com.henyep.ib.terminal.server.dao.RolePermissionsDao;

@Repository(value = "RolePermissionsDao")
public class RolePermissionsDaoImpl implements RolePermissionsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "RolePermissionsDao_SQLMap")
	Map<String, String> map;
	public RolePermissionsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveRolePermissions(RolePermissionsBean rolePermissions) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				rolePermissions.getRole_id(),
				rolePermissions.getPermission_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateRolePermissions(RolePermissionsBean rolePermissions) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				rolePermissions.getRole_id(),
				rolePermissions.getPermission_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RolePermissionsBean> getAllRolePermissionss() throws Exception{
		String sql = map.get("selectAll");
		List<RolePermissionsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RolePermissionsBean>(RolePermissionsBean.class));
		return beans;
	}

	@Override
	public List<RolePermissionsBean> getRolePermissionsByKey(Integer role_id, Integer permission_id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{role_id, permission_id};
		List<RolePermissionsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RolePermissionsBean>(RolePermissionsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteRolePermissions(Integer role_id, Integer permission_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{role_id, permission_id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
