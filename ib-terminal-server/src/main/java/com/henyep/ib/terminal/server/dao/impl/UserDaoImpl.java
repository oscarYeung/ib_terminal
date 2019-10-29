package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.UserBean;
import com.henyep.ib.terminal.server.dao.UserDao;

@Repository(value = "UserDao")
public class UserDaoImpl implements UserDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "UserDao_SQLMap")
	Map<String, String> map;
	public UserDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveUser(UserBean user) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				user.getUser_name(),
				user.getLast_update_time(),
				user.getStatus(),
				user.getUser_code(),
				user.getPassword(),
				user.getBrand_code()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateUser(UserBean user) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				user.getUser_name(),
				user.getStatus(),
				user.getLast_update_user(),
				user.getPassword(),
				user.getBrand_code(),
				user.getUser_code()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<UserBean> getAllUsers() throws Exception{
		String sql = map.get("selectAll");
		List<UserBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserBean>(UserBean.class));
		return beans;
	}

	@Override
	public List<UserBean> getUserByKey(String user_code) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{user_code};
		List<UserBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserBean>(UserBean.class), objs);
		return beans;
	}

	@Override
	public int deleteUser(String user_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{user_code};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public List<UserBean> getUserByUserPassword(String user_code, String password) throws Exception {
		
		final String sql = map.get("selectByUserPassword");
		Object[] objs = new Object[]{user_code, password};
		List<UserBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<UserBean>(UserBean.class), objs);
		return beans;
	}
	
	
}
