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

import com.henyep.ib.terminal.api.dto.db.ClientGroupMappingBean;
import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.server.dao.ClientGroupMappingDao;
import com.henyep.ib.terminal.server.dto.dao.IbClientMapDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "ClientGroupMappingDao")
public class ClientGroupMappingDaoImpl implements ClientGroupMappingDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "ClientGroupMappingDao_SQLMap")
	Map<String, String> map;
	
	@Resource(name = "sapJdbcTemplate_SapSource")
	JdbcTemplate sapJdbcTemplate;
	
	
	public ClientGroupMappingDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveClientGroupMapping(ClientGroupMappingBean clientGroupMapping) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				clientGroupMapping.getClient_trading_id(),
				clientGroupMapping.getGroup(),
				clientGroupMapping.getCreate_date(),
				clientGroupMapping.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}
	
	@Override
	public void saveClientGroupMapping(List<ClientGroupMappingBean> clientGroupMappingBeans, String lastUpdateUser) throws Exception {

		int batchSize = 3000;
		List<List<ClientGroupMappingBean>> beanBatchList = new ArrayList<List<ClientGroupMappingBean>>();

		while (clientGroupMappingBeans.size() > batchSize) {
			List<ClientGroupMappingBean> subList = clientGroupMappingBeans.subList(0, batchSize);
			beanBatchList.add(subList);
			clientGroupMappingBeans = clientGroupMappingBeans.subList(batchSize, clientGroupMappingBeans.size());

		}
		if (clientGroupMappingBeans.size() > 0) {
			beanBatchList.add(clientGroupMappingBeans);
		}

		for (List<ClientGroupMappingBean> beanList : beanBatchList) {

			String sql = map.get("create");

			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());

			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();

			for (ClientGroupMappingBean clientGroupMapping : beanList) {
			
				String ceateDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, clientGroupMapping.getCreate_date());
				Object[] objs = new Object[]{
						clientGroupMapping.getClient_trading_id(),
						clientGroupMapping.getGroup(),
						ceateDateString,
						lastUpdateUser
				};
				
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}

			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}


	@Override
	public int updateClientGroupMapping(ClientGroupMappingBean clientGroupMapping) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				clientGroupMapping.getGroup(),
				clientGroupMapping.getCreate_date(),
				clientGroupMapping.getLast_update_time(),
				clientGroupMapping.getLast_update_user(),
				clientGroupMapping.getClient_trading_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientGroupMappingBean> getAllClientGroupMappings() throws Exception{
		String sql = map.get("selectAll");
		List<ClientGroupMappingBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientGroupMappingBean>(ClientGroupMappingBean.class));
		return beans;
	}

	@Override
	public List<ClientGroupMappingBean> getClientGroupMappingByKey(String client_trading_id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{client_trading_id};
		List<ClientGroupMappingBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientGroupMappingBean>(ClientGroupMappingBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientGroupMapping(String client_trading_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{client_trading_id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public int deleteClientGroupMappingByCreateDate(Date create_date) throws Exception {
		final String sql = map.get("deleteByCreateDate");
		String createDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, create_date);
		Object[] objs = new Object[]{createDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public List<ClientGroupMappingBean> getFromSAP(Date tradeDate) throws Exception{
		String sql = map.get("selectFromSAP");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
		sql += " where regDate >= '" + tradeDateString + " 00:00:00' and regDate <= '" + tradeDateString + " 23:59:59'";
		List<ClientGroupMappingBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientGroupMappingBean>(ClientGroupMappingBean.class));
		return beans;
	}
	
	@Override
	public List<String> getVarSpreadClients() throws Exception{
		String sql = map.get("selectVarSpreadClient");
		List<String> clientTradingIds = this.jdbcTemplate.queryForList(sql, String.class);
		
		return clientTradingIds;
	}
	
	

	
}
