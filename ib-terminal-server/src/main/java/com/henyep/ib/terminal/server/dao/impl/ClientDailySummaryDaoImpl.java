package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientDailySummaryBean;
import com.henyep.ib.terminal.server.dao.ClientDailySummaryDao;

@Repository(value = "ClientDailySummaryDao")
public class ClientDailySummaryDaoImpl implements ClientDailySummaryDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "ClientDailySummaryDao_SQLMap")
	Map<String, String> map;
	public ClientDailySummaryDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveClientDailySummary(ClientDailySummaryBean clientDailySummary) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				clientDailySummary.getClient_code(),
				clientDailySummary.getTrade_date(),
				clientDailySummary.getCurrency(),
				clientDailySummary.getTotal_lot(),
				clientDailySummary.getTotal_deposit(),
				clientDailySummary.getTotal_deposit_usd(),
				clientDailySummary.getTotal_withdrawal(),
				clientDailySummary.getLast_update_time(),
				clientDailySummary.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateClientDailySummary(ClientDailySummaryBean clientDailySummary) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				clientDailySummary.getCurrency(),
				clientDailySummary.getTotal_lot(),
				clientDailySummary.getTotal_deposit(),
				clientDailySummary.getTotal_deposit_usd(),
				clientDailySummary.getTotal_withdrawal(),
				clientDailySummary.getLast_update_time(),
				clientDailySummary.getLast_update_user(),
				clientDailySummary.getClient_code(),
				clientDailySummary.getTrade_date()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientDailySummaryBean> getAllClientDailySummarys() throws Exception{
		String sql = map.get("selectAll");
		List<ClientDailySummaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDailySummaryBean>(ClientDailySummaryBean.class));
		return beans;
	}

	@Override
	public List<ClientDailySummaryBean> getClientDailySummaryByKey(String client_code, Date trade_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{client_code, trade_date};
		List<ClientDailySummaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDailySummaryBean>(ClientDailySummaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientDailySummary(String client_code, Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{client_code, trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public List<ClientDailySummaryBean> getClientDailySummarysByDateRange(Date start_date, Date end_date, List<String> clientList) throws Exception {
		String sql = map.get("selectByDateRange");
		if (clientList != null && clientList.size() > 0) {
			String clients = "'" + StringUtils.join(clientList, "', '") + "'";
			sql += " and client_code in (" + clients + ")";
		}
		
		Object[] objs = new Object[]{start_date, end_date};
		List<ClientDailySummaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDailySummaryBean>(ClientDailySummaryBean.class), objs);			
		return beans;
	}
	
}
