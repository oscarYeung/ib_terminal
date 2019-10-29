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

import com.henyep.ib.terminal.api.dto.db.ClientBalanceSummaryBean;
import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.response.clientProfile.SearchMyClientsModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbClientAccountModel;
import com.henyep.ib.terminal.server.dao.ClientDetailsDao;

@Repository(value = "ClientDetailsDao")
public class ClientDetailsDaoImpl implements ClientDetailsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	
	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;
	

	@Resource(name = "ClientDetailsDao_SQLMap")
	Map<String, String> map;
	public ClientDetailsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveClientDetails(ClientDetailsBean clientDetails) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				clientDetails.getFirst_name(),
				clientDetails.getSex(),
				clientDetails.getEmail(),
				clientDetails.getTrading_platform(),
				clientDetails.getLast_name(),
				clientDetails.getAccount_balance(),
				clientDetails.getCurrency(),
				clientDetails.getMobile(),
				clientDetails.getChinese_name(),
				clientDetails.getClient_trading_id(),
				clientDetails.getMt4_group(),
				clientDetails.getRegister_date(),
				clientDetails.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public void saveClientDetails(List<ClientDetailsBean> clientDetailsBeans) throws Exception {
		int batchSize = 5000;
		List<List<ClientDetailsBean>> beanBatchList = new ArrayList<List<ClientDetailsBean>>();
		
		while(clientDetailsBeans.size() > batchSize){
			List<ClientDetailsBean> subList = clientDetailsBeans.subList(0, batchSize);
			beanBatchList.add(subList);
			clientDetailsBeans = clientDetailsBeans.subList(batchSize, clientDetailsBeans.size());
		
		}
		if(clientDetailsBeans.size() > 0){
			beanBatchList.add(clientDetailsBeans);
		}
		
		for(List<ClientDetailsBean> beanList : beanBatchList){
				
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(ClientDetailsBean clientDetails : beanList){
				Object[] objs = new Object[]{
						clientDetails.getFirst_name(),
						clientDetails.getSex(),
						clientDetails.getEmail(),
						clientDetails.getTrading_platform(),
						clientDetails.getLast_name(),
						clientDetails.getAccount_balance(),
						clientDetails.getCurrency(),
						clientDetails.getMobile(),
						clientDetails.getChinese_name(),
						clientDetails.getClient_trading_id(),
						clientDetails.getMt4_group(),
						clientDetails.getRegister_date(),
						clientDetails.getLast_update_user()
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}

	@Override
	public int updateClientDetails(ClientDetailsBean clientDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				clientDetails.getFirst_name(),
				clientDetails.getLast_update_time(),
				clientDetails.getSex(),
				clientDetails.getEmail(),
				clientDetails.getTrading_platform(),
				clientDetails.getLast_name(),
				clientDetails.getLast_update_user(),
				clientDetails.getAccount_balance(),
				clientDetails.getCurrency(),
				clientDetails.getMobile(),
				clientDetails.getChinese_name(),
				clientDetails.getClient_trading_id()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientDetailsBean> getAllClientDetailss() throws Exception{
		String sql = map.get("selectAll");
		List<ClientDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDetailsBean>(ClientDetailsBean.class));
		return beans;
	}

	@Override
	public List<ClientDetailsBean> getClientDetailsByKey(String client_trading_id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{client_trading_id, client_trading_id}; 
		List<ClientDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDetailsBean>(ClientDetailsBean.class), objs);
		return beans;
	}
	
	public List<ClientDetailsBean> getClientDetailsByKeys(List<String> client_trading_ids) throws Exception{
		if(client_trading_ids.size() > 0){
			String sql = map.get("selectAll");
			sql += " where client_trading_id in ('" + StringUtils.join(client_trading_ids, "','") + "')";
			Object[] objs = new Object[]{}; 
			List<ClientDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDetailsBean>(ClientDetailsBean.class), objs);
			return beans;
		}
		else{
			return new ArrayList<ClientDetailsBean>(); 
		}
		
	}
	

	@Override
	public int deleteClientDetails(String client_trading_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{client_trading_id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int clearClientDetails() throws Exception {
		final String sql = map.get("clearClientDetails");
		Object[] objs = new Object[]{};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public List<ClientDetailsBean> getManuallyAddedClientDetails() throws Exception{
		final String sql = map.get("selectManuallyAddClientDetails");
		Object[] objs = new Object[]{}; 
		List<ClientDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDetailsBean>(ClientDetailsBean.class), objs);
		return beans;
		
	}
	
	
	@Override
	public List<IbClientAccountModel> getIbClientAccountSummary(String ib_code, Date trade_date) throws Exception {
		final String sql = map.get("getIbClientAccounts");
		Object[] objs = new Object[]{ib_code, ib_code};
		List<IbClientAccountModel> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientAccountModel>(IbClientAccountModel.class), objs);
		return beans;
	}
	
	@Override
	public List<ClientDetailsBean> getClientDetailsFromSAP() throws Exception {
		
		String sql = map.get("getClientDetailsFromSAP");
		Object[] objs = new Object[]{};
		List<ClientDetailsBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientDetailsBean>(ClientDetailsBean.class), objs);
		return beans;
	}
	@Override
	public List<SearchMyClientsModel> searchMyClients(String ib_code, String search_trading_id, String search_ib_code, String search_name,
			String search_phone, String search_email) throws Exception {
		String sql = map.get("searchMyClients");
		List<String> whereClauses = new ArrayList<String>();
		Object[] objs = new Object[]{ib_code, ib_code, ib_code, ib_code};
		if(search_trading_id != null && search_trading_id != ""){
			String whereClause = "A.client_code LIKE '%" + search_trading_id + "%'";
			whereClauses.add(whereClause);
		}
		if(search_ib_code != null && search_ib_code != ""){
			String whereClause = "A.ib_code LIKE '%" + search_ib_code + "%'";
			whereClauses.add(whereClause);
		}
		if(search_name != null && search_name != ""){
			String whereClause = "CD.first_name LIKE '%" + search_name + "%'";
			whereClauses.add(whereClause);
		}
		if(search_phone != null && search_phone != ""){
			String whereClause = "CD.mobile LIKE '%" + search_phone + "%'";
			whereClauses.add(whereClause);
		}
		if(search_email != null && search_email != ""){
			String whereClause = "CD.email LIKE '%" + search_email + "%'";
			whereClauses.add(whereClause);
		}
		
		if(whereClauses.size() > 0){
			sql += "WHERE " + StringUtils.join(whereClauses.toArray(), " AND ");
		}
		
		List<SearchMyClientsModel> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SearchMyClientsModel>(SearchMyClientsModel.class), objs);
		return beans;
	}
	
}
