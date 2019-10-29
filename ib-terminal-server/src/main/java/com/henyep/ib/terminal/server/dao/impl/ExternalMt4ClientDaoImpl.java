package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ExternalMt4ClientBean;
import com.henyep.ib.terminal.server.dao.ExternalMt4ClientDao;

@Repository(value = "ExternalMt4ClientDao")
public class ExternalMt4ClientDaoImpl implements ExternalMt4ClientDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "ExternalMt4ClientDao_SQLMap")
	Map<String, String> map;
	public ExternalMt4ClientDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveExternalMt4Client(ExternalMt4ClientBean externalMt4Client) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				externalMt4Client.getLast_update_time(),
				externalMt4Client.getMt4_group(),
				externalMt4Client.getClient_account(),
				externalMt4Client.getLast_update_user(),
				externalMt4Client.getCurrency()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateExternalMt4Client(ExternalMt4ClientBean externalMt4Client) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				externalMt4Client.getLast_update_time(),
				externalMt4Client.getMt4_group(),
				externalMt4Client.getLast_update_user(),
				externalMt4Client.getCurrency(),
				externalMt4Client.getClient_account()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ExternalMt4ClientBean> getAllExternalMt4Clients() throws Exception{
		String sql = map.get("selectAll");
		List<ExternalMt4ClientBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ExternalMt4ClientBean>(ExternalMt4ClientBean.class));
		return beans;
	}

	@Override
	public List<ExternalMt4ClientBean> getExternalMt4ClientByKey(int client_account) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{client_account};
		List<ExternalMt4ClientBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ExternalMt4ClientBean>(ExternalMt4ClientBean.class), objs);
		return beans;
	}

	@Override
	public int deleteExternalMt4Client(int client_account) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{client_account};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
