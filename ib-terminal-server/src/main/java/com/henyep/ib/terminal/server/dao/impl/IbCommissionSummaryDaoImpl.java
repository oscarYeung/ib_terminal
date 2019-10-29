package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbCommissionSummaryBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbMonthBalanceModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbSummaryByDateRangeModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.summary.IbCommissionSummaryWebModel;
import com.henyep.ib.terminal.api.dto.response.user.CurrentMothData;
import com.henyep.ib.terminal.server.dao.IbCommissionSummaryDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dto.report.IbProductGroupSummaryDto;
import com.henyep.ib.terminal.server.dto.report.IbSummaryDto;
import com.henyep.ib.terminal.server.dto.report.OwnerIbDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbCommissionSummaryDao")
public class IbCommissionSummaryDaoImpl implements IbCommissionSummaryDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbCommissionSummaryDao_SQLMap")
	Map<String, String> map;
	
	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;

	public IbCommissionSummaryDaoImpl() throws Exception {
		super();
	}

	@Override
	public int updateIbCommissionSummary(IbCommissionSummaryBean ibCommissionSummary) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibCommissionSummary.getTotal_spread_commission(), ibCommissionSummary.getLast_update_time(),
				ibCommissionSummary.getTotal_ev_commission(), ibCommissionSummary.getTotal_rebate_commission_pip(),
				ibCommissionSummary.getTotal_rebate_commission_lot(), ibCommissionSummary.getTotal_commission(),
				ibCommissionSummary.getTotal_fix_commission(), ibCommissionSummary.getTotal_lot(), ibCommissionSummary.getLast_update_user(),
				ibCommissionSummary.getTotal_rev_commission(), ibCommissionSummary.getCurrency(), ibCommissionSummary.getNet_deposit(),
				ibCommissionSummary.getPlatform(), ibCommissionSummary.getTrade_date(), ibCommissionSummary.getIb_code(),
				ibCommissionSummary.getProduct_group(), ibCommissionSummary.getBrand_code() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbCommissionSummaryBean> getAllIbCommissionSummarys() throws Exception {
		String sql = map.get("selectAll");
		List<IbCommissionSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionSummaryBean>(IbCommissionSummaryBean.class));
		return beans;
	}

	@Override
	public List<IbCommissionSummaryBean> getIbCommissionSummaryByKey(String platform, Date trade_date, String ib_code, String product_group,
			String brand_code) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { platform, trade_date, ib_code, product_group, brand_code };
		List<IbCommissionSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionSummaryBean>(IbCommissionSummaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbCommissionSummary(String platform, Date trade_date, String ib_code, String product_group, String brand_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { platform, trade_date, ib_code, product_group, brand_code };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public void generateIbCommissionSummarys(Date startDate, Date endDate) throws Exception {
		final String sql = map.get("generateSummary");
		Object[] objs = new Object[] { startDate, endDate };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	public List<IbCommissionSummaryWebModel> getIbCommissionSummaryByTeamHead(String ibCode, Date startDate, Date endDate) throws Exception {
		final String sql = map.get("selectByDateRangeIbCodeForWebClient");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[] { startDateString, endDateString, ibCode, endDateString, endDateString, endDateString };
		List<IbCommissionSummaryWebModel> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionSummaryWebModel>(IbCommissionSummaryWebModel.class), objs);
		return beans;
	}

	@Override
	public CurrentMothData calculateIbCommissionByDate(String ibCode, String startDate, String endDate) throws Exception {
		final String sql = map.get("calculateIbCommissionByDateAndIbCode");

		Object[] objs = new Object[] { ibCode, startDate, endDate };
		CurrentMothData data = this.jdbcTemplate.queryForObject(sql, objs, new BeanPropertyRowMapper<CurrentMothData>(CurrentMothData.class));
		return data;
	}

	@Override
	public List<IbSummaryByDateRangeModel> getIbSummaryByDateRange(String ibCode, Date startDate, Date endDate)
			throws Exception {
		final String sql = map.get("selectIbSummary");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		
		Object[] objs = new Object[] { ibCode, ibCode, startDateString, endDateString, startDateString, endDateString, ibCode};
		List<IbSummaryByDateRangeModel> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbSummaryByDateRangeModel>(IbSummaryByDateRangeModel.class), objs);
		return beans;
	}
	
	@Override
	public List<IbSummaryByDateRangeModel> getIbSummaryByLastTradeDate(String ibCode) throws Exception {
		final String sql = map.get("selectLastTradeDayIbSummary");
		
		Object[] objs = new Object[] { ibCode, ibCode, ibCode};
		List<IbSummaryByDateRangeModel> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbSummaryByDateRangeModel>(IbSummaryByDateRangeModel.class), objs);
		return beans;
	}
	
	
	
	public IbMonthBalanceModel getTrimmedIbSummaryByDateRange(String ibCode, Date startDate, Date endDate)
			throws Exception {
		final String sql = map.get("selectTrimmedIbSummary");
		Object[] objs = new Object[] { ibCode, startDate, endDate};
		IbMonthBalanceModel bean = null;
		try{
			bean = this.jdbcTemplate.queryForObject(sql, objs, new BeanPropertyRowMapper<IbMonthBalanceModel>(IbMonthBalanceModel.class));
		}catch(Exception ex){
			bean = null;
		}
		return bean;
	}

	@Override
	public int deleteIbCommissionSummaryByDateRange(Date start_date, Date end_date) throws Exception {
		
		final String sql = map.get("deleteByDateRange");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { startDateString, endDateString,};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
		
	}

	@Override
	public int deleteIbCommissionSummaryByDateRangeIbCodes(Date start_date, Date end_date, List<String> ib_codes)
			throws Exception {
		
		String sql = map.get("deleteByDateRange");
		
		sql += " AND ib_code in ('" + StringUtils.join(ib_codes, "','") + "')";
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { startDateString, endDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public Date getLastTradeDate(){
		String sql = map.get("getLastTradeDay");
		
		Date lastTradeDate = this.jdbcTemplate.queryForObject(sql, Date.class);
		return lastTradeDate;
	}
	
	@Override
	public List<IbCommissionSummaryBean> getIbCommissionSummarysByBrandCodeIbCodeDateRange(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code) throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectByDateRange");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		Object[] objs = new Object[] { startDateString, endDateString, brand_code};
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<IbCommissionSummaryBean> beans = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper<IbCommissionSummaryBean>(IbCommissionSummaryBean.class));
		return beans;
	}
	
	@Override
	public List<IbCommissionSummaryBean> getIbCommissionPeriodSummary(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code) throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectByDateRangePeriod");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		Object[] objs = new Object[] { startDateString, endDateString, brand_code};
		
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<IbCommissionSummaryBean> beans = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper<IbCommissionSummaryBean>(IbCommissionSummaryBean.class));
		return beans;
	}

	@Override
	public List<IbSummaryDto> getIbCommissionSummarysByIbCodeDateRange(Date start_date, Date end_date, String brand_code, String ib_code) throws Exception {
		String sql = map.get("selectByDateRangeGroupByIbCode");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		Object[] objs = new Object[] { startDateString, endDateString, brand_code, ib_code};
		if(ib_code.equals("")){
			sql = StringUtils.replace(sql, " AND ib_code = ?", "");
			objs = new Object[] { startDateString, endDateString, brand_code};
		}
		
		List<IbSummaryDto> beans = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper<IbSummaryDto>(IbSummaryDto.class));
		return beans;
		
	}
	
	@Override
	public List<IbSummaryDto> getIbCommissionSummarysByUserCode(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code, String jurisdiction) throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectByDateRangeUserCode");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		Object[] objs = new Object[] { startDateString, endDateString, brand_code, jurisdiction
				, startDateString, endDateString
				, startDateString, endDateString, brand_code
				, startDateString, endDateString
				, startDateString, endDateString, brand_code};
		if(jurisdiction == null){
			objs = new Object[] { startDateString, endDateString, brand_code 
					, startDateString, endDateString
					, startDateString, endDateString, brand_code
					, startDateString, endDateString
					, startDateString, endDateString, brand_code};
			sql = StringUtils.replace(sql, "AND jurisdiction = ?", "");
		}
		
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<IbSummaryDto> beans = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper<IbSummaryDto>(IbSummaryDto.class));


		return beans;
		
	}
	
	@Override
	public List<IbProductGroupSummaryDto> getIbCommissionSummarysProductGroup(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code, String jurisdiction) throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectGroupByProductGroupIbCode");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		Object[] objs = new Object[] { startDateString, endDateString, brand_code};
		if(jurisdiction != null){
			objs = new Object[] { startDateString, endDateString, brand_code, jurisdiction};
		}
		else{
			sql = StringUtils.replace(sql, " AND jurisdiction = ?", "");
		}
		
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<IbProductGroupSummaryDto> beans = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper<IbProductGroupSummaryDto>(IbProductGroupSummaryDto.class));
		return beans;
	}

	@Override
	public List<OwnerIbDto> getOwnerIbDtos() throws Exception{
		
		String sql = map.get("getOwnerIbMinIdMaxId");
		Object[] objs = new Object[] {};
		List<OwnerIbDto> beans = this.jdbcTemplate.query(sql, objs, new BeanPropertyRowMapper<OwnerIbDto>(OwnerIbDto.class));
		return beans;
	}
	
	
	
	public Map<String, String> getIbCommissionSummaryRelatedOwners(Date startDate, Date endDate, String brandCode, String sender) throws Exception{
		
		List<IbSummaryDto> beanList = getIbCommissionSummarysByUserCode(startDate, endDate, brandCode, null, sender, null);
		
		List<OwnerIbDto> ownerIbDtoList = getOwnerIbDtos();
		Map<String, String> ownerEmailMap = new HashMap<String, String>();
		for (IbSummaryDto bean : beanList) {
			String ibOwner = "";
			for(OwnerIbDto ownerIbDto : ownerIbDtoList){
				if(ownerIbDto.getIb_max_id() >= bean.getMax_id() && ownerIbDto.getIb_min_id() <= bean.getMin_id()){
					ibOwner = ownerIbDto.getUser_code();
					if(!ownerEmailMap.containsKey(ibOwner)){
						ownerEmailMap.put(ibOwner, ownerIbDto.getEmail());
					}
				}
			}
			
		}
		return ownerEmailMap;
	}
	
	

}
