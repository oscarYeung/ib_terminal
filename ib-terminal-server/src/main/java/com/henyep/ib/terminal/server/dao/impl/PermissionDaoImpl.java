package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.PermissionBean;
import com.henyep.ib.terminal.server.dao.PermissionDao;

@Repository(value = "PermissionDao")
public class PermissionDaoImpl implements PermissionDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "PermissionDao_SQLMap")
	Map<String, String> map;

	public PermissionDaoImpl() throws Exception {
		super();
	}

	@Override
	public int savePermission(PermissionBean permission) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { permission.getId(), permission.getKey() };
		return this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updatePermission(PermissionBean permission) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { permission.getKey(), permission.getId() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<PermissionBean> getAllPermissions() throws Exception {
		String sql = map.get("selectAll");
		List<PermissionBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<PermissionBean>(PermissionBean.class));
		return beans;
	}

	@Override
	public List<PermissionBean> getPermissionByKey(Integer id) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { id };
		List<PermissionBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<PermissionBean>(PermissionBean.class), objs);
		return beans;
	}

	@Override
	public int deletePermission(Integer id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { id };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<PermissionBean> getPermissionListByActiveUser(String user_code) throws Exception {
		final String sql = map.get("getPermissionListByActiveUser");
		Object[] objs = new Object[] { user_code };
		List<PermissionBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<PermissionBean>(PermissionBean.class), objs);
		return list;
	}
}
