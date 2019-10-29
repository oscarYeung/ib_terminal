package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.UserPermissionsBean;
import com.henyep.ib.terminal.server.dao.UserPermissionsDao;

@Repository(value = "UserPermissionsDao")
public class UserPermissionsDaoImpl implements UserPermissionsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "UserPermissionsDao_SQLMap")
	Map<String, String> map;
	public UserPermissionsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveUserPermissions(UserPermissionsBean userPermissions) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				userPermissions.getUser_code(),
				userPermissions.getPermission_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateUserPermissions(UserPermissionsBean userPermissions) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				userPermissions.getUser_code(),
				userPermissions.getPermission_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<UserPermissionsBean> getAllUserPermissionss() throws Exception{
		String sql = map.get("selectAll");
		List<UserPermissionsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserPermissionsBean>(UserPermissionsBean.class));
		return beans;
	}

	@Override
	public List<UserPermissionsBean> getUserPermissionsByKey(String user_code, Integer permission_id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{user_code, permission_id};
		List<UserPermissionsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserPermissionsBean>(UserPermissionsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteUserPermissions(String user_code, Integer permission_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{user_code, permission_id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
