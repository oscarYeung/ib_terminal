package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.api.dto.db.OpenTradeBean;
import com.henyep.ib.terminal.server.dao.OpenTradeDao;

@Repository(value = "OpenTradeDao")
public class OpenTradeDaoImpl implements OpenTradeDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;	
	
	@Resource(name = "OpenTradeDao_SQLMap")
	Map<String, String> map;
	public OpenTradeDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveOpenTrade(OpenTradeBean openTrade) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				openTrade.getTrade_date(),
				openTrade.getTicket(),
				openTrade.getClient_code(),
				openTrade.getPlatform(),
				openTrade.getBs(),
				openTrade.getLot(),
				openTrade.getSymbol(),
				openTrade.getPl(),
				openTrade.getCommission(),
				openTrade.getSwap(),
				openTrade.getAccount_idenify(),
				openTrade.getOpen_price(),
				openTrade.getOpen_time()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}
	
	@Override
	public void saveOpenTrades(List<OpenTradeBean> openTradesList) throws Exception {
		
		int batchSize = 3000;
		List<List<OpenTradeBean>> beanBatchList = new ArrayList<List<OpenTradeBean>>();
		
		while(openTradesList.size() > batchSize){
			List<OpenTradeBean> subList = openTradesList.subList(0, batchSize);
			beanBatchList.add(subList);
			openTradesList = openTradesList.subList(batchSize, openTradesList.size());
		
		}
		if(openTradesList.size() > 0){
			beanBatchList.add(openTradesList);
		}
		
		for(List<OpenTradeBean> beanList : beanBatchList){
				
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(OpenTradeBean openTrade : beanList){
				Object[] objs = new Object[]{
						openTrade.getTrade_date(),
						openTrade.getTicket(),
						openTrade.getClient_code(),
						openTrade.getPlatform(),
						openTrade.getBs(),
						openTrade.getLot(),
						openTrade.getSymbol(),
						openTrade.getPl(),
						openTrade.getCommission(),
						openTrade.getSwap(),
						openTrade.getAccount_idenify(),
						openTrade.getOpen_price(),
						openTrade.getOpen_time()
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}


	@Override
	public int updateOpenTrade(OpenTradeBean openTrade) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				openTrade.getClient_code(),
				openTrade.getPlatform(),
				openTrade.getBs(),
				openTrade.getLot(),
				openTrade.getSymbol(),
				openTrade.getPl(),
				openTrade.getCommission(),
				openTrade.getSwap(),
				openTrade.getAccount_idenify(),
				openTrade.getOpen_price(),
				openTrade.getOpen_time(),
				openTrade.getTrade_date(),
				openTrade.getTicket()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<OpenTradeBean> getAllOpenTrades() throws Exception{
		String sql = map.get("selectAll");
		List<OpenTradeBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<OpenTradeBean>(OpenTradeBean.class));
		return beans;
	}

	@Override
	public List<OpenTradeBean> getOpenTradeByKey(Date trade_date, String ticket) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{trade_date, ticket};
		List<OpenTradeBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<OpenTradeBean>(OpenTradeBean.class), objs);
		return beans;
	}

	@Override
	public int deleteOpenTrade(String tradeDateString) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{tradeDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public List<OpenTradeBean> getOpenTradeFromSAP(String tradeDate) throws Exception {
		
		String sql = map.get("sap_selectByTradeDate");
		Object[] objs = new Object[]{tradeDate};
		List<OpenTradeBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<OpenTradeBean>(OpenTradeBean.class), objs);
		return beans;
	}
}
