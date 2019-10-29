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
import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.server.dao.ClientTradeDetailsDao;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "ClientTradeDetailsDao")
public class ClientTradeDetailsDaoImpl implements ClientTradeDetailsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;
	
	
	@Resource(name = "ClientTradeDetailsDao_SQLMap")
	Map<String, String> map;
	public ClientTradeDetailsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveClientTradeDetails(ClientTradeDetailsBean clientTradeDetails) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				clientTradeDetails.getTicket(),
				clientTradeDetails.getGroup_code(),
				clientTradeDetails.getJurisdiction(),
				clientTradeDetails.getSpread_type(),
				clientTradeDetails.getClient_code(),
				clientTradeDetails.getPlatform(),
				clientTradeDetails.getBs(),
				clientTradeDetails.getLot(),
				clientTradeDetails.getSymbol(),
				clientTradeDetails.getPl(),
				clientTradeDetails.getCommission(),
				clientTradeDetails.getSwap(),
				clientTradeDetails.getAccount_identify(),
				clientTradeDetails.getOpen_price(),
				clientTradeDetails.getClose_price(),
				clientTradeDetails.getTrade_date(),
				clientTradeDetails.getOpen_time(),
				clientTradeDetails.getClose_time()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}
	
	@Override
	public void saveClientTradeDetailsList(List<ClientTradeDetailsBean> clientTradeDetailsList) throws Exception {
		
		int batchSize = 3000;
		List<List<ClientTradeDetailsBean>> beanBatchList = new ArrayList<List<ClientTradeDetailsBean>>();
		
		while(clientTradeDetailsList.size() > batchSize){
			List<ClientTradeDetailsBean> subList = clientTradeDetailsList.subList(0, batchSize);
			beanBatchList.add(subList);
			clientTradeDetailsList = clientTradeDetailsList.subList(batchSize, clientTradeDetailsList.size());
		
		}
		if(clientTradeDetailsList.size() > 0){
			beanBatchList.add(clientTradeDetailsList);
		}
		
		for(List<ClientTradeDetailsBean> beanList : beanBatchList){
				
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(ClientTradeDetailsBean clientTradeDetails : beanList){
				Object[] objs = new Object[]{
						clientTradeDetails.getTicket(),
						clientTradeDetails.getGroup_code(),
						clientTradeDetails.getJurisdiction(),
						clientTradeDetails.getSpread_type(),
						clientTradeDetails.getClient_code(),
						clientTradeDetails.getPlatform(),
						clientTradeDetails.getBs(),
						clientTradeDetails.getLot(),
						clientTradeDetails.getSymbol(),
						clientTradeDetails.getPl(),
						clientTradeDetails.getCommission(),
						clientTradeDetails.getSwap(),
						clientTradeDetails.getAccount_identify(),
						clientTradeDetails.getOpen_price(),
						clientTradeDetails.getClose_price(),
						clientTradeDetails.getTrade_date(),
						clientTradeDetails.getOpen_time(),
						clientTradeDetails.getClose_time()
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}


	@Override
	public int updateClientTradeDetails(ClientTradeDetailsBean clientTradeDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				clientTradeDetails.getJurisdiction(),
				clientTradeDetails.getSpread_type(),
				clientTradeDetails.getClient_code(),
				clientTradeDetails.getPlatform(),
				clientTradeDetails.getBs(),
				clientTradeDetails.getSymbol(),
				clientTradeDetails.getPl(),
				clientTradeDetails.getCommission(),
				clientTradeDetails.getSwap(),
				clientTradeDetails.getOpen_price(),
				clientTradeDetails.getClose_price(),
				clientTradeDetails.getTrade_date(),
				clientTradeDetails.getOpen_time(),
				clientTradeDetails.getClose_time(),
				clientTradeDetails.getTicket()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientTradeDetailsBean> getAllClientTradeDetailss() throws Exception{
		String sql = map.get("selectAll");
		List<ClientTradeDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientTradeDetailsBean>(ClientTradeDetailsBean.class));
		return beans;
	}

	@Override
	public List<ClientTradeDetailsBean> getClientTradeDetailsByKey(String ticket) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{ticket};
		List<ClientTradeDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientTradeDetailsBean>(ClientTradeDetailsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientTradeDetails(String ticket) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{ticket};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public List<ClientTradeDetailsBean> getClientTradeDetailsByTradeDate(Date tradeDate) throws Exception {
		final String sql = map.get("selectByTradeDate");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
		Object[] objs = new Object[]{tradeDateString};
		List<ClientTradeDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientTradeDetailsBean>(ClientTradeDetailsBean.class), objs);
		return beans;
	}
	
	@Override
	public List<ClientTradeDetailsBean> getClientTradeDetailsByCltCodesDateRange(List<String> clientCodes,
			Date startDate, Date endDate) throws Exception {
		
		String sql = map.get("selectAll");
		List<Object> objList = new ArrayList<Object>();
		
		List<String> whereClauses = new ArrayList<String>();
		if(clientCodes != null && clientCodes.size() > 0){
			String whereClause = "client_code in ('" + StringUtils.join(clientCodes, "', '") + "')";
			whereClauses.add(whereClause);
		}		
		whereClauses.add("trade_date >= ?");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		objList.add(startDateString);
		
		if(endDate != null){
			String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
			whereClauses.add("trade_date <= ?");
			objList.add(endDateString);
		}
		
		sql += " WHERE " + StringUtils.join(whereClauses, " AND ") + " ORDER BY client_code,trade_date";
		Object[] objs = objList.toArray();
		
		List<ClientTradeDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientTradeDetailsBean>(ClientTradeDetailsBean.class), objs);
		return beans;
	}
	
	public List<ClientTradeDetailsBean> getClientTradeDetailsByDateRange(Date startDate, Date endDate) throws Exception{
		String sql = map.get("selectAll");
		List<Object> objList = new ArrayList<Object>();
		
		List<String> whereClauses = new ArrayList<String>();
		
		whereClauses.add("trade_date >= ?");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		objList.add(startDateString);
		
		if(endDate != null){
			String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
			whereClauses.add("trade_date <= ?");
			objList.add(endDateString);
		}
		
		sql += " WHERE " + StringUtils.join(whereClauses, " AND ") + " ORDER BY client_code,trade_date";
		Object[] objs = objList.toArray();
		
		List<ClientTradeDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientTradeDetailsBean>(ClientTradeDetailsBean.class), objs);
		return beans;
	}
	
	@Override
	public List<ClientTradeDetailsBean> getClientTradeDetailsFromSAP(String tradeDate) throws Exception {
		
		String sql = map.get("sap_selectByTradeDate");
		Object[] objs = new Object[]{tradeDate};
		List<ClientTradeDetailsBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientTradeDetailsBean>(ClientTradeDetailsBean.class), objs);
		return beans;
	}
	
	@Override
	public int clearClientTradeDetails(String tradeDate) throws Exception {
		final String sql = map.get("deleteByTradeDate");
		Object[] objs = new Object[]{tradeDate};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	
	
}
