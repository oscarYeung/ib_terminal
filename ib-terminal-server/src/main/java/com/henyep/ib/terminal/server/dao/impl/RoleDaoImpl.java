package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.RoleBean;
import com.henyep.ib.terminal.server.dao.RoleDao;

@Repository(value = "RoleDao")
public class RoleDaoImpl implements RoleDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "RoleDao_SQLMap")
	Map<String, String> map;
	public RoleDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveRole(RoleBean role) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				role.getId(),
				role.getDescription()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateRole(RoleBean role) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				role.getDescription(),
				role.getId()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RoleBean> getAllRoles() throws Exception{
		String sql = map.get("selectAll");
		List<RoleBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RoleBean>(RoleBean.class));
		return beans;
	}

	@Override
	public List<RoleBean> getRoleByKey(Integer id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{id};
		List<RoleBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RoleBean>(RoleBean.class), objs);
		return beans;
	}

	@Override
	public int deleteRole(Integer id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
