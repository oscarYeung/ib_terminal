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

import com.henyep.ib.terminal.api.dto.db.IbDailyFloatingBean;
import com.henyep.ib.terminal.api.dto.db.OpenTradeBean;
import com.henyep.ib.terminal.server.dao.IbDailyFloatingDao;
import com.henyep.ib.terminal.server.dto.report.ClientFloatingPNLDto;
import com.henyep.ib.terminal.server.dto.report.IbFloatingPNLDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbDailyFloatingDao")
public class IbDailyFloatingDaoImpl implements IbDailyFloatingDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;	
	
	@Resource(name = "IbDailyFloatingDao_SQLMap")
	Map<String, String> map;
	public IbDailyFloatingDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveIbDailyFloating(IbDailyFloatingBean ibDailyFloating) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				ibDailyFloating.getIb_code(),
				ibDailyFloating.getAccount_number(),
				ibDailyFloating.getTrade_date(),
				ibDailyFloating.getFloating_pnl(),
				ibDailyFloating.getSwap(),
				ibDailyFloating.getCommission(),
				ibDailyFloating.getProfit(),
				ibDailyFloating.getPnl_adj(),
				ibDailyFloating.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}
	

	@Override
	public void saveIbDailyFloatings(List<IbDailyFloatingBean> ibDailyFloatingList, String last_update_user) throws Exception {
		
		int batchSize = 3000;
		List<List<IbDailyFloatingBean>> beanBatchList = new ArrayList<List<IbDailyFloatingBean>>();
		
		while(ibDailyFloatingList.size() > batchSize){
			List<IbDailyFloatingBean> subList = ibDailyFloatingList.subList(0, batchSize);
			beanBatchList.add(subList);
			ibDailyFloatingList = ibDailyFloatingList.subList(batchSize, ibDailyFloatingList.size());
		
		}
		if(ibDailyFloatingList.size() > 0){
			beanBatchList.add(ibDailyFloatingList);
		}
		
		for(List<IbDailyFloatingBean> beanList : beanBatchList){
				
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(IbDailyFloatingBean ibDailyFloating : beanList){
				Object[] objs = new Object[]{
						ibDailyFloating.getIb_code(),
						ibDailyFloating.getAccount_number(),
						ibDailyFloating.getTrade_date(),
						ibDailyFloating.getFloating_pnl(),
						ibDailyFloating.getSwap(),
						ibDailyFloating.getCommission(),
						ibDailyFloating.getProfit(),
						ibDailyFloating.getPnl_adj(),
						last_update_user
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			
			
			
			int res = this.jdbcTemplate.update(sql, objList.toArray());
			
			
		}
	}


	@Override
	public int updateIbDailyFloating(IbDailyFloatingBean ibDailyFloating) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				ibDailyFloating.getFloating_pnl(),
				ibDailyFloating.getSwap(),
				ibDailyFloating.getCommission(),
				ibDailyFloating.getProfit(),
				ibDailyFloating.getPnl_adj(),
				ibDailyFloating.getIb_code(),
				ibDailyFloating.getAccount_number(),
				ibDailyFloating.getTrade_date(),
				ibDailyFloating.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbDailyFloatingBean> getAllIbDailyFloatings() throws Exception{
		String sql = map.get("selectAll");
		List<IbDailyFloatingBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbDailyFloatingBean>(IbDailyFloatingBean.class));
		return beans;
	}

	@Override
	public List<IbDailyFloatingBean> getIbDailyFloatingByKey(String ib_code, Date trade_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{ib_code, trade_date};
		List<IbDailyFloatingBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbDailyFloatingBean>(IbDailyFloatingBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbDailyFloating(String ib_code, Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{ib_code, trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteIbDailyFloatingByTradeDate(String trade_date) throws Exception {
		final String sql = map.get("deleteByTradeDate");
		Object[] objs = new Object[]{trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteIbDailyFloatingByLessThanTradeDate(String trade_date) throws Exception {
		final String sql = map.get("deleteByLessThanTradeDate");
		Object[] objs = new Object[]{trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public List<IbDailyFloatingBean> getFromSAP(Date trade_date) throws Exception{
		final String sql = map.get("getFromSAP");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_SHORT, trade_date);
		Object[] objs = new Object[]{tradeDateString};
		List<IbDailyFloatingBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<IbDailyFloatingBean>(IbDailyFloatingBean.class), objs);
		return beans;
		
	}
	
	@Override
	public List<IbFloatingPNLDto> getIbFloatingPNLDto(Date start_date, Date end_date) throws Exception{
		final String sql = map.get("getFloatingPNL");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[]{startDateString, endDateString};
		List<IbFloatingPNLDto> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbFloatingPNLDto>(IbFloatingPNLDto.class), objs);
		return beans;
		
	}
	
	@Override
	public List<ClientFloatingPNLDto> getIbClientFloatingPNLDto(Date start_date, Date end_date) throws Exception{
		final String sql = map.get("getClientFloatingPNLByTradeDate");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[]{startDateString, endDateString};
		List<ClientFloatingPNLDto> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientFloatingPNLDto>(ClientFloatingPNLDto.class), objs);
		return beans;
	}
	
}
