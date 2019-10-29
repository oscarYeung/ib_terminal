package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.SettingsUsdCurrencyExchangeBean;
import com.henyep.ib.terminal.server.dao.SettingsUsdCurrencyExchangeDao;

@Repository(value = "SettingsUsdCurrencyExchangeDao")
public class SettingsUsdCurrencyExchangeDaoImpl implements SettingsUsdCurrencyExchangeDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "SettingsUsdCurrencyExchangeDao_SQLMap")
	Map<String, String> map;
	public SettingsUsdCurrencyExchangeDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveSettingsUsdCurrencyExchange(SettingsUsdCurrencyExchangeBean settingsUsdCurrencyExchange) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				settingsUsdCurrencyExchange.getBase_currency(),
				settingsUsdCurrencyExchange.getExchange_symbol(),
				settingsUsdCurrencyExchange.getIs_cross(),
				settingsUsdCurrencyExchange.getLast_update_time()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateSettingsUsdCurrencyExchange(SettingsUsdCurrencyExchangeBean settingsUsdCurrencyExchange) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				settingsUsdCurrencyExchange.getExchange_symbol(),
				settingsUsdCurrencyExchange.getIs_cross(),
				settingsUsdCurrencyExchange.getLast_update_time(),
				settingsUsdCurrencyExchange.getBase_currency()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<SettingsUsdCurrencyExchangeBean> getAllSettingsUsdCurrencyExchanges() throws Exception{
		String sql = map.get("selectAll");
		List<SettingsUsdCurrencyExchangeBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsUsdCurrencyExchangeBean>(SettingsUsdCurrencyExchangeBean.class));
		return beans;
	}

	@Override
	public List<SettingsUsdCurrencyExchangeBean> getSettingsUsdCurrencyExchangeByKey(String base_currency) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{base_currency};
		List<SettingsUsdCurrencyExchangeBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsUsdCurrencyExchangeBean>(SettingsUsdCurrencyExchangeBean.class), objs);
		return beans;
	}

	@Override
	public int deleteSettingsUsdCurrencyExchange(String base_currency) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{base_currency};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
