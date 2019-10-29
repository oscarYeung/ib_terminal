package com.henyep.ib.terminal.server.dao.impl;

import java.util.Date;
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

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.db.RebateSupplementaryBean;
import com.henyep.ib.terminal.server.dao.RebateSupplementaryDao;

@Repository(value = "RebateSupplementaryDao")
public class RebateSupplementaryDaoImpl implements RebateSupplementaryDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "RebateSupplementaryDao_SQLMap")
	Map<String, String> map;

	public RebateSupplementaryDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveRebateSupplementary(RebateSupplementaryBean rebateSupplementary) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { rebateSupplementary.getCurrency(), rebateSupplementary.getEv_percentage(), 
				rebateSupplementary.getEnd_date(), rebateSupplementary.getRebate_code(),
				rebateSupplementary.getGroup_code(),rebateSupplementary.getLast_update_user(),
				rebateSupplementary.getStart_date() };
		int res = this.jdbcTemplate.update(sql, objs);

	}
	
	@Override
	public void saveRebateSupplementary(List<RebateSupplementaryBean> rebateSupplementaries) throws Exception {
		
		String sql = map.get("create");
		
		int contentPos = sql.indexOf("(?");
		String insertStatement = sql.substring(0, contentPos);
		String valueContent = sql.substring(contentPos, sql.length());
		
		List<String> valueContentList = new ArrayList<String>();
		ArrayList<Object> objList = new ArrayList<Object>();
		
		for(RebateSupplementaryBean rebateSupplementary : rebateSupplementaries){
			Object[] objs = new Object[]{
					rebateSupplementary.getCurrency(),
					rebateSupplementary.getEv_percentage(),
					rebateSupplementary.getEnd_date(),
					rebateSupplementary.getRebate_code(),
					rebateSupplementary.getGroup_code(),
					rebateSupplementary.getLast_update_user(),
					rebateSupplementary.getStart_date()
			};
			objList.addAll(Arrays.asList(objs));
			valueContentList.add(valueContent);
		}
		
		sql = insertStatement + StringUtils.join(valueContentList, ",");
		int res = this.jdbcTemplate.update(sql, objList.toArray());
	}

	@Override
	public int updateRebateSupplementary(RebateSupplementaryBean rebateSupplementary) throws Exception {
		final String sql = map.get("update");
	Object[] objs = new Object[]{
				rebateSupplementary.getCurrency(),
				rebateSupplementary.getEv_percentage(),
				rebateSupplementary.getGroup_code(),
				rebateSupplementary.getEnd_date(),
				rebateSupplementary.getLast_update_user(),
				rebateSupplementary.getRebate_code(),
				rebateSupplementary.getStart_date()
		};		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int updateRebateSupplementary(List<RebateSupplementaryBean> rebateSupplementaries) throws Exception {
		int totalCount = 0;
		for(RebateSupplementaryBean rebateSupplementary : rebateSupplementaries){
			totalCount += updateRebateSupplementary(rebateSupplementary);
		}
		return totalCount;
	}
	
	@Override
	public List<RebateSupplementaryBean> getAllRebateSupplementarys() throws Exception{		String sql = map.get("selectAll");
		List<RebateSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<RebateSupplementaryBean>(RebateSupplementaryBean.class));
		return beans;
	}

	@Override
	public List<RebateSupplementaryBean> getRebateSupplementaryByKey(String rebate_code, Date start_date) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { rebate_code, start_date };
		List<RebateSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<RebateSupplementaryBean>(RebateSupplementaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteRebateSupplementary(String rebate_code, Date start_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { rebate_code, start_date };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RebateSupplementaryBean> getRebateSupplementaryByDateRange(Date start_date, Date end_date) {
		String sql = map.get("getByDateRange");
		Object[] objs = new Object[] { start_date, end_date };
		List<RebateSupplementaryBean> list = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<RebateSupplementaryBean>(RebateSupplementaryBean.class), objs);
		return list;
	}@Override
	public int deleteRebateSupplementary(List<RebateSupplementaryBean> rebateSupplementaries) throws Exception {
		
		int totalCount = 0;
		for(RebateSupplementaryBean rebateSupplementary : rebateSupplementaries){
			totalCount += deleteRebateSupplementary(rebateSupplementary.getRebate_code(), rebateSupplementary.getStart_date());
		}
		return totalCount;
	}
	
	@Override
	public List<RebateSupplementaryBean> getRebateSupplementaryByRebateCode(String rebate_code) throws Exception {
		final String sql = map.get("selectByRebateCode");
		Object[] objs = new Object[]{rebate_code};
		List<RebateSupplementaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateSupplementaryBean>(RebateSupplementaryBean.class), objs);
		return beans;
	}

	@Override
	public List<RebateSupplementaryBean> getRebateSupplementaryByRebateCodeWithDateRange(String rebate_code, Date start_date, Date end_date)
			throws Exception {
		final String sql = map.get("selectByRebateCodeWithDayRange");
		Object[] objs = new Object[]{rebate_code, start_date, end_date};
		List<RebateSupplementaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateSupplementaryBean>(RebateSupplementaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteRebateSupplementary(String rebate_code) throws Exception {
		final String sql = map.get("deleteByRebateCode");
		Object[] objs = new Object[] { rebate_code};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
