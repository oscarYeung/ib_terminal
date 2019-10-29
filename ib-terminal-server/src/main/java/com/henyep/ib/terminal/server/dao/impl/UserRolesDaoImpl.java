package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.UserRolesBean;
import com.henyep.ib.terminal.server.dao.UserRolesDao;

@Repository(value = "UserRolesDao")
public class UserRolesDaoImpl implements UserRolesDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "UserRolesDao_SQLMap")
	Map<String, String> map;
	public UserRolesDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveUserRoles(UserRolesBean userRoles) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				userRoles.getUser_code(),
				userRoles.getRole_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateUserRoles(UserRolesBean userRoles) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				userRoles.getUser_code(),
				userRoles.getRole_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<UserRolesBean> getAllUserRoless() throws Exception{
		String sql = map.get("selectAll");
		List<UserRolesBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserRolesBean>(UserRolesBean.class));
		return beans;
	}

	@Override
	public List<UserRolesBean> getUserRolesByKey(String user_code, Integer role_id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{user_code, role_id};
		List<UserRolesBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserRolesBean>(UserRolesBean.class), objs);
		return beans;
	}

	@Override
	public int deleteUserRoles(String user_code, Integer role_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{user_code, role_id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public Integer getUserRolesByUserCode(String user_code) throws Exception{
		final String sql = map.get("selectByUserCode");
		Object[] objs = new Object[]{user_code};
		List<UserRolesBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserRolesBean>(UserRolesBean.class), objs);
		if(beans.size() > 0){
			return beans.get(0).getRole_id();
		}
		else
			return null;
	}
}
