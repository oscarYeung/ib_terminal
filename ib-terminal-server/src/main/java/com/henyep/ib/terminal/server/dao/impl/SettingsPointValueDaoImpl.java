package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.SettingsPointValueBean;
import com.henyep.ib.terminal.server.dao.SettingsPointValueDao;

@Repository(value = "SettingsPointValueDao")
public class SettingsPointValueDaoImpl implements SettingsPointValueDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "SettingsPointValueDao_SQLMap")
	Map<String, String> map;
	public SettingsPointValueDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveSettingsPointValue(SettingsPointValueBean settingsPointValue) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				settingsPointValue.getAmount(),
				settingsPointValue.getLast_update_time(),
				settingsPointValue.getEnd_date(),
				settingsPointValue.getSymbol(),
				settingsPointValue.getLast_update_user(),
				settingsPointValue.getStart_date(),
				settingsPointValue.getCurrency()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateSettingsPointValue(SettingsPointValueBean settingsPointValue) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				settingsPointValue.getAmount(),
				settingsPointValue.getLast_update_time(),
				settingsPointValue.getEnd_date(),
				settingsPointValue.getLast_update_user(),
				settingsPointValue.getCurrency(),
				settingsPointValue.getSymbol(),
				settingsPointValue.getStart_date()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<SettingsPointValueBean> getAllSettingsPointValues() throws Exception{
		String sql = map.get("selectAll");
		List<SettingsPointValueBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsPointValueBean>(SettingsPointValueBean.class));
		return beans;
	}

	@Override
	public List<SettingsPointValueBean> getSettingsPointValueByKey(String symbol, Date start_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{symbol, start_date};
		List<SettingsPointValueBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsPointValueBean>(SettingsPointValueBean.class), objs);
		return beans;
	}

	@Override
	public int deleteSettingsPointValue(String symbol, Date start_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{symbol, start_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
