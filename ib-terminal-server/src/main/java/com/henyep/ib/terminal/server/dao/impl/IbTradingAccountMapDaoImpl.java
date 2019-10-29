package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbTradingAccountMapBean;
import com.henyep.ib.terminal.server.dao.IbTradingAccountMapDao;

@Repository(value = "IbTradingAccountMapDao")
public class IbTradingAccountMapDaoImpl implements IbTradingAccountMapDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbTradingAccountMapDao_SQLMap")
	Map<String, String> map;
	public IbTradingAccountMapDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveIbTradingAccountMap(IbTradingAccountMapBean ibTradingAccountMap) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				ibTradingAccountMap.getTrading_account(),
				ibTradingAccountMap.getLast_update_time(),
				ibTradingAccountMap.getTrading_platform(),
				ibTradingAccountMap.getLast_update_user(),
				ibTradingAccountMap.getIb_code()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateIbTradingAccountMap(IbTradingAccountMapBean ibTradingAccountMap) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				ibTradingAccountMap.getTrading_account(),
				ibTradingAccountMap.getLast_update_time(),
				ibTradingAccountMap.getTrading_platform(),
				ibTradingAccountMap.getLast_update_user(),
				ibTradingAccountMap.getIb_code()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbTradingAccountMapBean> getAllIbTradingAccountMaps() throws Exception{
		String sql = map.get("selectAll");
		List<IbTradingAccountMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTradingAccountMapBean>(IbTradingAccountMapBean.class));
		return beans;
	}

	@Override
	public List<IbTradingAccountMapBean> getIbTradingAccountMapByKey(String ib_code) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{ib_code};
		List<IbTradingAccountMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbTradingAccountMapBean>(IbTradingAccountMapBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbTradingAccountMap(String ib_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{ib_code};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
