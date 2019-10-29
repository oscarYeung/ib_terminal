package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.SettingsMt4GroupBean;
import com.henyep.ib.terminal.server.dao.SettingsMt4GroupDao;

@Repository(value = "SettingsMt4GroupDao")
public class SettingsMt4GroupDaoImpl implements SettingsMt4GroupDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "SettingsMt4GroupDao_SQLMap")
	Map<String, String> map;
	public SettingsMt4GroupDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveSettingsMt4Group(SettingsMt4GroupBean settingsMt4Group) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				settingsMt4Group.getSpread_commission(),
				settingsMt4Group.getLast_update_time(),
				settingsMt4Group.getEnd_date(),
				settingsMt4Group.getMt4_group(),
				settingsMt4Group.getLast_update_user(),
				settingsMt4Group.getFix_commission(),
				settingsMt4Group.getStart_date(),
				settingsMt4Group.getProduct_group(),
				settingsMt4Group.getCurrency()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateSettingsMt4Group(SettingsMt4GroupBean settingsMt4Group) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				settingsMt4Group.getSpread_commission(),
				settingsMt4Group.getLast_update_time(),
				settingsMt4Group.getEnd_date(),
				settingsMt4Group.getLast_update_user(),
				settingsMt4Group.getFix_commission(),
				settingsMt4Group.getCurrency(),
				settingsMt4Group.getMt4_group(),
				settingsMt4Group.getStart_date(),
				settingsMt4Group.getProduct_group()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<SettingsMt4GroupBean> getAllSettingsMt4Groups() throws Exception{
		String sql = map.get("selectAll");
		List<SettingsMt4GroupBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsMt4GroupBean>(SettingsMt4GroupBean.class));
		return beans;
	}

	@Override
	public List<SettingsMt4GroupBean> getSettingsMt4GroupByKey(String mt4_group, Date start_date, String product_group) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{mt4_group, start_date, product_group};
		List<SettingsMt4GroupBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsMt4GroupBean>(SettingsMt4GroupBean.class), objs);
		return beans;
	}

	@Override
	public int deleteSettingsMt4Group(String mt4_group, Date start_date, String product_group) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{mt4_group, start_date, product_group};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
