package com.henyep.ib.terminal.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.henyep.ib.terminal.api.dto.db.MarginInBean;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.MarginInDao;
import com.henyep.ib.terminal.server.dto.marginInOut.MarginInOutDto;
import com.henyep.ib.terminal.server.service.impl.MarginInServiceImpl;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "MarginInDao")
public class MarginInDaoImpl implements MarginInDao{

	final static Logger log = Logger.getLogger(MarginInDaoImpl.class);
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;
	
	@Resource(name = "MarginInDao_SQLMap")
	Map<String, String> map;
	public MarginInDaoImpl() throws Exception{
		super();
	}
	
	@Override
	public int saveMarginIn(final MarginInBean marginIn) throws Exception {
		
		
		final String sql = map.get("create");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
		           
		        	ps.setDouble(1, marginIn.getExchange_rate() == null ? 0 : marginIn.getExchange_rate());
		        	ps.setString(2, marginIn.getStatus());
		        	ps.setString(3, marginIn.getBrand_code());
		        	ps.setString(4, marginIn.getCurrency());
		        	ps.setDouble(5, marginIn.getAmount() == null ? 0 : marginIn.getAmount());
		        	ps.setString(6, marginIn.getCategory());
		        	ps.setString(7, marginIn.getAccount());
		        	ps.setInt(8, marginIn.getTransfer_id() == null ? 0 : marginIn.getTransfer_id());
		        	ps.setString(9, marginIn.getCreate_user());
		        	ps.setString(10, marginIn.getAccount_currency());
		        	ps.setString(11, marginIn.getComment());
		        	ps.setDouble(12, marginIn.getAccount_amount() == null ? 0 : marginIn.getAccount_amount());
		        	ps.setTimestamp(13, new Timestamp(marginIn.getTrade_date().getTime()));
		        	ps.setString(14, marginIn.getLast_update_user());
		            return ps;
		        }
		    }, keyHolder);
		return keyHolder.getKey().intValue();

	}

	@Override
	@Transactional
	public void saveMarginIns(List<MarginInBean> marginInBeans) throws Exception {
		for(MarginInBean marginInBean : marginInBeans){
			saveMarginIn(marginInBean);
		}
		
	}

	@Override
	public int updateMarginIn(MarginInBean marginIn) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				marginIn.getExchange_rate(),
				marginIn.getStatus(),
				marginIn.getBrand_code(),
				marginIn.getCurrency(),
				marginIn.getAmount(),
				marginIn.getCategory(),
				marginIn.getAccount(),
				marginIn.getTransfer_id(),
				marginIn.getLast_update_user(),
				marginIn.getAccount_currency(),
				marginIn.getComment(),
				marginIn.getAccount_amount(),
				marginIn.getTrade_date(),
				marginIn.getId()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<MarginInBean> getAllMarginIns() throws Exception{
		String sql = map.get("selectAll");
		List<MarginInBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginInBean>(MarginInBean.class));
		return beans;
	}

	@Override
	public List<MarginInBean> getMarginInByKey(int id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{id};
		List<MarginInBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginInBean>(MarginInBean.class), objs);
		return beans;
	}

	@Override
	public int deleteMarginIn(int id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public int deleteMarginInDateByDateRange(Date startDate, Date endDate, String comment, String updatedBy) throws Exception {
		
		final String sql = map.get("clearMarginInDateByDateRange");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[]{comment, updatedBy, startDateString, endDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public int deleteMarginInDateByDateRangeIbCodes(Date startDate, Date endDate, List<String> ibCodes, String comment, String updatedBy) throws Exception {
		
		String sql = map.get("clearMarginInDateByDateRange");
		
		sql += " AND account in ('" + StringUtils.join(ibCodes, "','") + "')";
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[]{comment, updatedBy, startDateString, endDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public List<MarginInBean> getMarginInByUserCodeExample(Date startDate, Date endDate, MarginInBean marginInBean, String userCode, String brandCode)
			throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(userCode, brandCode);
		
		String sql = map.get("selectByUserCode");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		
		if(startDate != null){
			whereClauses.add("trade_date >= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate));	
		}
		
		if(endDate != null){
			whereClauses.add("trade_date <= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate));
		}
		
		if(marginInBean != null){
			if(marginInBean.getStatus() != null){
				whereClauses.add("status = ?");
				objList.add(marginInBean.getStatus());
			}
			
			if(marginInBean.getAccount() != null){
				whereClauses.add("account = ?");
				objList.add(marginInBean.getAccount());
			}
			
			if(marginInBean.getBrand_code() != null){
				whereClauses.add("brand_code = ?");
				objList.add(marginInBean.getBrand_code());
			}
			
			if(marginInBean.getId() != null){
				whereClauses.add("id = ?");
				objList.add(marginInBean.getId());
			}
			
			if(marginInBean.getCategory() != null){
				whereClauses.add("category = ?");
				objList.add(marginInBean.getCategory());
			}
			
			
		}

		if(whereClauses.size() > 0 && objList.size() > 0){
			sqlBuilder.append(" AND ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		sql += " ORDER BY trade_date DESC, account DESC";
		
		log.info(sql);
		List<MarginInBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginInBean>(MarginInBean.class), objList.toArray());
		
		return beans;
		
	}
	
	
	@Override
	public List<MarginInBean> getMarginInByExample(Date startDate, Date endDate, MarginInBean marginInBean)
			throws Exception {
		
		String sql = map.get("selectAll");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		
		if(startDate != null){
			whereClauses.add("trade_date >= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate));	
		}
		
		if(endDate != null){
			whereClauses.add("trade_date <= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate));
		}
		
		if(marginInBean != null){
			if(marginInBean.getStatus() != null){
				whereClauses.add("status = ?");
				objList.add(marginInBean.getStatus());
			}
			
			if(marginInBean.getAccount() != null){
				whereClauses.add("account = ?");
				objList.add(marginInBean.getAccount());
			}
			
			if(marginInBean.getBrand_code() != null){
				whereClauses.add("brand_code = ?");
				objList.add(marginInBean.getBrand_code());
			}
			
			if(marginInBean.getId() != null){
				whereClauses.add("id = ?");
				objList.add(marginInBean.getId());
			}
			
			if(marginInBean.getCategory() != null){
				whereClauses.add("category = ?");
				objList.add(marginInBean.getCategory());
			}
			
			
		}

		if(whereClauses.size() > 0 && objList.size() > 0){
			sqlBuilder.append(" WHERE ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}
		
		
		sql += " ORDER BY trade_date DESC, account DESC";
		
		log.info(sql);
		List<MarginInBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginInBean>(MarginInBean.class), objList.toArray());
		
		return beans;
		
	}

	@Override
	public List<MarginInBean> getMarginInByKeys(List<String> ids) throws Exception {
		if(ids.size() > 0){
			String sql = map.get("selectAll");
			String idsString = "'" + StringUtils.join(ids, "','") + "'";
			sql += " WHERE id in (" + idsString + ")";
			List<MarginInBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginInBean>(MarginInBean.class));
			return beans;
		}
		else{
			return new ArrayList<MarginInBean>();
		}
	}
	
	@Override
	public List<MarginInOutDto> getMonthlyMarginInOutReport(Date start_date, Date end_date, String brand_code, List<String> ib_codes) throws Exception {
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		String tradeDateWhereClause = " AND trade_date >= '"+startDateString+"' AND trade_date <= '"+endDateString+"'";
		String brandCodeWhereClause = "  AND brand_code = '"+brand_code+"'";
		String ibCodesWhereClause = null;
		if(ib_codes.size() > 0){
			ibCodesWhereClause = " AND account IN ('" + StringUtils.join(ib_codes.toArray(), "','") + "')";
		}
		String sql = map.get("monthlyMarginInOutReport");
		if(ibCodesWhereClause != null){
			sql = sql.replaceAll("#account_where_clause", ibCodesWhereClause);
		}
		else{
			sql = sql.replaceAll("#account_where_clause", "");
		}
		sql = sql.replaceAll("#brand_code_where_clause", brandCodeWhereClause);
		sql = sql.replaceAll("#trade_date_where_clause", tradeDateWhereClause);
		List<MarginInOutDto> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginInOutDto>(MarginInOutDto.class));
		return beans;
		
	}
	
	
	
}
