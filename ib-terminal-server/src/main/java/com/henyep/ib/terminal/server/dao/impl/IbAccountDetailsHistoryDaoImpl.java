package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbAccountDetailsHistoryBean;
import com.henyep.ib.terminal.server.dao.IbAccountDetailsHistoryDao;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbAccountDetailsHistoryDao")
public class IbAccountDetailsHistoryDaoImpl implements IbAccountDetailsHistoryDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbAccountDetailsHistoryDao_SQLMap")
	Map<String, String> map;
	public IbAccountDetailsHistoryDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveIbAccountDetailsHistory(IbAccountDetailsHistoryBean ibAccountDetailsHistory) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				ibAccountDetailsHistory.getIb_code(),
				ibAccountDetailsHistory.getCurrency(),
				ibAccountDetailsHistory.getTrade_date(),
				ibAccountDetailsHistory.getAccount_balance(),
				ibAccountDetailsHistory.getPending_commission(),
				ibAccountDetailsHistory.getDay_open(),
				ibAccountDetailsHistory.getDay_open_pending_commission(),
				ibAccountDetailsHistory.getMonth_to_date(),
				ibAccountDetailsHistory.getYear_to_date(),
				ibAccountDetailsHistory.getNet_margin_bonus(),
				ibAccountDetailsHistory.getLast_update_time(),
				ibAccountDetailsHistory.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateIbAccountDetailsHistory(IbAccountDetailsHistoryBean ibAccountDetailsHistory) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				ibAccountDetailsHistory.getAccount_balance(),
				ibAccountDetailsHistory.getPending_commission(),
				ibAccountDetailsHistory.getDay_open(),
				ibAccountDetailsHistory.getDay_open_pending_commission(),
				ibAccountDetailsHistory.getMonth_to_date(),
				ibAccountDetailsHistory.getYear_to_date(),
				ibAccountDetailsHistory.getNet_margin_bonus(),
				ibAccountDetailsHistory.getLast_update_time(),
				ibAccountDetailsHistory.getLast_update_user(),
				ibAccountDetailsHistory.getIb_code(),
				ibAccountDetailsHistory.getCurrency(),
				ibAccountDetailsHistory.getTrade_date()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbAccountDetailsHistoryBean> getAllIbAccountDetailsHistorys() throws Exception{
		String sql = map.get("selectAll");
		List<IbAccountDetailsHistoryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbAccountDetailsHistoryBean>(IbAccountDetailsHistoryBean.class));
		return beans;
	}

	@Override
	public List<IbAccountDetailsHistoryBean> getIbAccountDetailsHistoryByKey(String ib_code, String currency, Date trade_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{ib_code, currency, trade_date};
		List<IbAccountDetailsHistoryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbAccountDetailsHistoryBean>(IbAccountDetailsHistoryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbAccountDetailsHistory(String ib_code, String currency, Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{ib_code, currency, trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteByTradeDate(Date trade_date) throws Exception {
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		final String sql = map.get("deleteByTradeDate");
		Object[] objs = new Object[]{tradeDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int insertFromIbAccountDetails(Date trade_date) throws Exception {
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		final String sql = map.get("insertFromIbAccountDetails");
		Object[] objs = new Object[]{tradeDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
}
