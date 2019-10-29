package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.SettingsCfdBean;
import com.henyep.ib.terminal.server.dao.SettingsCfdDao;

@Repository(value = "SettingsCfdDao")
public class SettingsCfdDaoImpl implements SettingsCfdDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "SettingsCfdDao_SQLMap")
	Map<String, String> map;
	public SettingsCfdDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveSettingsCfd(SettingsCfdBean settingsCfd) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				settingsCfd.getLast_update_time(),
				settingsCfd.getEnd_date(),
				settingsCfd.getSymbol(),
				settingsCfd.getAmount_per_unit(),
				settingsCfd.getLast_update_user(),
				settingsCfd.getStart_date(),
				settingsCfd.getCurrency()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateSettingsCfd(SettingsCfdBean settingsCfd) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				settingsCfd.getLast_update_time(),
				settingsCfd.getEnd_date(),
				settingsCfd.getAmount_per_unit(),
				settingsCfd.getLast_update_user(),
				settingsCfd.getCurrency(),
				settingsCfd.getSymbol(),
				settingsCfd.getStart_date()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<SettingsCfdBean> getAllSettingsCfds() throws Exception{
		String sql = map.get("selectAll");
		List<SettingsCfdBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsCfdBean>(SettingsCfdBean.class));
		return beans;
	}

	@Override
	public List<SettingsCfdBean> getSettingsCfdByKey(String symbol, Date start_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{symbol, start_date};
		List<SettingsCfdBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsCfdBean>(SettingsCfdBean.class), objs);
		return beans;
	}

	@Override
	public int deleteSettingsCfd(String symbol, Date start_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{symbol, start_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
