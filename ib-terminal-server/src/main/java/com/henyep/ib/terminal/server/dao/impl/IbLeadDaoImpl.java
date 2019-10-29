package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientDetailsBean;
import com.henyep.ib.terminal.api.dto.db.IbLeadBean;
import com.henyep.ib.terminal.server.dao.IbLeadDao;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbLeadDao")
public class IbLeadDaoImpl implements IbLeadDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;
	
	@Resource(name = "IbLeadDao_SQLMap")
	Map<String, String> map;

	public IbLeadDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveIbLead(IbLeadBean ibLead) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibLead.getIb_code(), ibLead.getSalesforce_id(), ibLead.getClient_first_name(), ibLead.getClient_last_name(),
				ibLead.getPhone(), ibLead.getEmail(), ibLead.getRegistration_date(), ibLead.getRegister_type(), ibLead.getStatus(),
				ibLead.getLast_update_time(), ibLead.getLast_update_user() };
		int res = this.jdbcTemplate.update(sql, objs);
	}
	
	@Override
	public void saveIbLeads(List<IbLeadBean> ibLeads) throws Exception {
		int batchSize = 5000;
		List<List<IbLeadBean>> beanBatchList = new ArrayList<List<IbLeadBean>>();
		
		while(ibLeads.size() > batchSize){
			List<IbLeadBean> subList = ibLeads.subList(0, batchSize);
			beanBatchList.add(subList);
			ibLeads = ibLeads.subList(batchSize, ibLeads.size());
		
		}
		if(ibLeads.size() > 0){
			beanBatchList.add(ibLeads);
		}
		
		for(List<IbLeadBean> beanList : beanBatchList){
				
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(IbLeadBean ibLead : beanList){
				Object[] objs = new Object[] { ibLead.getIb_code(), ibLead.getSalesforce_id(), ibLead.getClient_first_name(), ibLead.getClient_last_name(),
						ibLead.getPhone(), ibLead.getEmail(), ibLead.getRegistration_date(), ibLead.getRegister_type(), ibLead.getStatus(),
						ibLead.getLast_update_time(), ibLead.getLast_update_user() };
				
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}

	@Override
	public int updateIbLead(IbLeadBean ibLead) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibLead.getIb_code(), ibLead.getClient_first_name(), ibLead.getClient_last_name(), ibLead.getPhone(),
				ibLead.getEmail(), ibLead.getRegistration_date(), ibLead.getRegister_type(), ibLead.getStatus(), ibLead.getLast_update_time(),
				ibLead.getLast_update_user(), ibLead.getSalesforce_id() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbLeadBean> getAllIbLeads() throws Exception {
		String sql = map.get("selectAll");
		List<IbLeadBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbLeadBean>(IbLeadBean.class));
		return beans;
	}

	@Override
	public List<IbLeadBean> getIbLeadByKey(String salesforce_id) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { salesforce_id };
		List<IbLeadBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbLeadBean>(IbLeadBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbLead(String salesforce_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { salesforce_id };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int deleteIbLeads(List<String> salesforce_ids) throws Exception {
		String sql = map.get("deleteIbLeads");
		sql += "('" + StringUtils.join(salesforce_ids, "','") + "')";
		Object[] objs = new Object[] { };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int deleteIbLeadsByTradeDate(Date tradeDate) throws Exception {
		String sql = map.get("deleteIbLeadsByTradeDate");
		Object[] objs = new Object[] { tradeDate };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbLeadBean> getLeads(String ib_code, String name, String email, String phone) {
		String sql = map.get("getLeads");
		Object[] objs = new Object[] { ib_code };
		
		List<String> whereClauses = new ArrayList<String>();
		if(name != null && !name.equals("")){
			whereClauses.add("(client_first_name like '%" + name + "%' or client_last_name like '%" + name + "%')");
		}
		if(email != null && !email.equals("")){
			whereClauses.add("email like '%" + email + "%'");
		}
		if(phone != null && !phone.equals("")){
			whereClauses.add("phone like '%" + phone + "%'");
		}
		if(whereClauses.size() > 0){
			sql += "AND " + StringUtils.join(whereClauses, " AND ");
		}

		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbLeadBean>(IbLeadBean.class), objs);
	}

	@Override
	public List<IbLeadBean> getLeadsWithSubIb(String ib_code, String name, String email, String phone) {
		String sql = map.get("getLeadsWithSubIb");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date today = calendar.getTime();
		Object[] objs = new Object[] { ib_code, ib_code, ib_code, today, today, today };
		
		List<String> whereClauses = new ArrayList<String>();
		if(name != null && !name.equals("")){
			whereClauses.add("(client_first_name like '%" + name + "%' or client_last_name like '%" + name + "%')");
		}
		if(email != null && !email.equals("")){
			whereClauses.add("email like '%" + email + "%'");
		}
		if(phone != null && !phone.equals("")){
			whereClauses.add("phone like '%" + phone + "%'");
		}
		if(whereClauses.size() > 0){
			sql = sql.replace("#WhereClauses", " AND " + StringUtils.join(whereClauses, " AND "));
		}
		else{
			sql = sql.replace("#WhereClauses", "");
		}
		
		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbLeadBean>(IbLeadBean.class), objs);
	}

	@Override
	public List<String> getDeletedLeadFromSAP(Date tradeDate) {
		final String sql = map.get("getDeletedLeadFromSAP");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
		Object[] objs = new Object[] { tradeDateString };
		return this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<String>(String.class), objs);
	}
	
	@Override
	public List<IbLeadBean> getNewLeadFromSAP(Date tradeDate) {
		final String sql = map.get("getNewLeadFromSAP");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, tradeDate);
		Object[] objs = new Object[] { tradeDateString };
		return this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<IbLeadBean>(IbLeadBean.class), objs);
	}
	
}
