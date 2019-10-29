package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientMarginInOutBean;
import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.server.dao.ClientMarginInOutDao;

@Repository(value = "ClientMarginInOutDao")
public class ClientMarginInOutDaoImpl implements ClientMarginInOutDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;

	@Resource(name = "ClientMarginInOutDao_SQLMap")
	Map<String, String> map;
	public ClientMarginInOutDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveClientMarginInOut(ClientMarginInOutBean clientMarginInOut) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				clientMarginInOut.getAmount(),
				clientMarginInOut.getAmountUsd(),
				clientMarginInOut.getClient_code(),
				clientMarginInOut.getPlatform(),
				clientMarginInOut.getTrade_date(),
				clientMarginInOut.getComment(),
				clientMarginInOut.getTrade_type_id(),
				clientMarginInOut.getOrder_id(),
				clientMarginInOut.getCurrency(),
				clientMarginInOut.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public void saveClientMarginInOuts(List<ClientMarginInOutBean> clientMarginInOuts) throws Exception {
		
		int batchSize = 3000;
		List<List<ClientMarginInOutBean>> beanBatchList = new ArrayList<List<ClientMarginInOutBean>>();
		
		while(clientMarginInOuts.size() > batchSize){
			List<ClientMarginInOutBean> subList = clientMarginInOuts.subList(0, batchSize);
			beanBatchList.add(subList);
			clientMarginInOuts = clientMarginInOuts.subList(batchSize, clientMarginInOuts.size());
		
		}
		if(clientMarginInOuts.size() > 0){
			beanBatchList.add(clientMarginInOuts);
		}
		
		for(List<ClientMarginInOutBean> beanList : beanBatchList){
				
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(ClientMarginInOutBean clientMarginInOut : beanList){
				Object[] objs = new Object[]{
						clientMarginInOut.getAmount(),
						clientMarginInOut.getAmountUsd(),
						clientMarginInOut.getClient_code(),
						clientMarginInOut.getPlatform(),
						clientMarginInOut.getTrade_date(),
						clientMarginInOut.getComment(),
						clientMarginInOut.getTrade_type_id(),
						clientMarginInOut.getOrder_id(),
						clientMarginInOut.getCurrency(),
						clientMarginInOut.getLast_update_user()
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
		
	}


	@Override
	public int updateClientMarginInOut(ClientMarginInOutBean clientMarginInOut) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				clientMarginInOut.getAmount(),
				clientMarginInOut.getAmountUsd(),
				clientMarginInOut.getClient_code(),
				clientMarginInOut.getPlatform(),
				clientMarginInOut.getTrade_date(),
				clientMarginInOut.getComment(),
				clientMarginInOut.getTrade_type_id(),
				clientMarginInOut.getCurrency(),
				clientMarginInOut.getOrder_id(),
				clientMarginInOut.getLast_update_time(),
				clientMarginInOut.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientMarginInOutBean> getAllClientMarginInOuts() throws Exception{
		String sql = map.get("selectAll");
		List<ClientMarginInOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class));
		return beans;
	}

	@Override
	public List<ClientMarginInOutBean> getClientMarginInOutByKey(String order_id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{order_id};
		List<ClientMarginInOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientMarginInOut(String order_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{order_id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public List<ClientMarginInOutBean> getClientMarginByDateClientCodes(Date startDate, Date endDate,
			List<String> cltCodes) throws Exception {
		
		List<ClientMarginInOutBean> beans = new ArrayList<ClientMarginInOutBean>();
		if(cltCodes.size() > 0){
			String sql = map.get("selectByDateRange");
			
			List<String> quotedCltCodes = new ArrayList<String>();
			for(String cltCode : cltCodes){
				quotedCltCodes.add("'" + cltCode + "'");
			}
			
			sql += " AND client_code in (" + StringUtils.join(quotedCltCodes, ",") + ")";
			
			Object[] objs = new Object[]{startDate, endDate};
			beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class), objs);
		}
		
		return beans;
		
		
	}
	@Override
	public List<ClientMarginInOutBean> getClientMarginGroupByDate(String cltCode, Date startDate, Date endDate)
			throws Exception {
		
		String sql = map.get("selectGroupByDate");
		Object[] objs = new Object[]{cltCode, startDate, endDate};
		List<ClientMarginInOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class), objs);
		return beans;
	}
	@Override
	public List<ClientMarginInOutBean> getByIbCode(String ibCode, Date startDate, Date endDate) throws Exception {
		String sql = map.get("getByIbCode");
		Object[] objs = new Object[]{ibCode, startDate, endDate};
		List<ClientMarginInOutBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class), objs);
		return list;
	}
	@Override
	public List<ClientMarginInOutBean> getByClientCode(String ibCode, String ClientCode, Date startDate, Date endDate) throws Exception {
		String sql = map.get("getByClientCode");
		Object[] objs = new Object[]{ibCode, ClientCode, startDate, endDate};
		List<ClientMarginInOutBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class), objs);
		return list;
	}
	
	@Override
	public List<ClientMarginInOutBean> getClientMarginInOutFromSAP(String tradeDate) throws Exception {
		
		String sql = map.get("sap_selectClientMarginByTradeDate");
		Object[] objs = new Object[]{tradeDate};
		List<ClientMarginInOutBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientMarginInOutBean>(ClientMarginInOutBean.class), objs);
		return beans;
	}
	
	
	@Override
	public int clearClientMarginInOut(String tradeDate) throws Exception {
		final String sql = map.get("deleteByTradeDate");
		Object[] objs = new Object[]{tradeDate};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
		
	}
}
