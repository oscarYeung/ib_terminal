package com.henyep.ib.terminal.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbCommissionClientSummaryBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbClientSummariesModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbCommissionDetailsModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.IbCommissionModel;
import com.henyep.ib.terminal.api.dto.response.ibcommission.clientSummary.IbClientCommissionSummaryWebModel;
import com.henyep.ib.terminal.server.dao.IbCommissionClientSummaryDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dto.report.ClientSummaryPeriodDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbCommissionClientSummaryDao")
public class IbCommissionClientSummaryDaoImpl implements IbCommissionClientSummaryDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbCommissionClientSummaryDao_SQLMap")
	Map<String, String> map;

	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;

	public IbCommissionClientSummaryDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveIbCommissionClientSummary(IbCommissionClientSummaryBean ibCommissionClientSummary) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibCommissionClientSummary.getClient_ib_code(), ibCommissionClientSummary.getPlatform(),
				ibCommissionClientSummary.getTotal_spread_commission(), ibCommissionClientSummary.getLast_update_time(),
				ibCommissionClientSummary.getTotal_trade_swaps(), ibCommissionClientSummary.getTotal_rebate_commission_lot(),
				ibCommissionClientSummary.getTotal_commission(), ibCommissionClientSummary.getTotal_lot(),
				ibCommissionClientSummary.getProduct_group(), ibCommissionClientSummary.getBrand_code(), ibCommissionClientSummary.getCurrency(),
				ibCommissionClientSummary.getClient_code(), ibCommissionClientSummary.getTotal_rebate_commission_pip(),
				ibCommissionClientSummary.getTrade_date(), ibCommissionClientSummary.getTotal_fix_commission(),
				ibCommissionClientSummary.getTotal_trade_pl(), ibCommissionClientSummary.getLast_update_user(),
				ibCommissionClientSummary.getIb_code(), ibCommissionClientSummary.getNet_deposit() };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updateIbCommissionClientSummary(IbCommissionClientSummaryBean ibCommissionClientSummary) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibCommissionClientSummary.getTotal_spread_commission(), ibCommissionClientSummary.getLast_update_time(),
				ibCommissionClientSummary.getTotal_trade_swaps(), ibCommissionClientSummary.getTotal_rebate_commission_pip(),
				ibCommissionClientSummary.getTotal_rebate_commission_lot(), ibCommissionClientSummary.getTotal_commission(),
				ibCommissionClientSummary.getTotal_fix_commission(), ibCommissionClientSummary.getTotal_trade_pl(),
				ibCommissionClientSummary.getTotal_lot(), ibCommissionClientSummary.getLast_update_user(), ibCommissionClientSummary.getCurrency(),
				ibCommissionClientSummary.getNet_deposit(), ibCommissionClientSummary.getClient_ib_code(), ibCommissionClientSummary.getPlatform(),
				ibCommissionClientSummary.getClient_code(), ibCommissionClientSummary.getTrade_date(), ibCommissionClientSummary.getIb_code(),
				ibCommissionClientSummary.getProduct_group(), ibCommissionClientSummary.getBrand_code() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbCommissionClientSummaryBean> getAllIbCommissionClientSummarys() throws Exception {
		String sql = map.get("selectAll");
		List<IbCommissionClientSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionClientSummaryBean>(IbCommissionClientSummaryBean.class));
		return beans;
	}

	@Override
	public List<IbCommissionClientSummaryBean> getIbCommissionClientSummaryByKey(String client_ib_code, String platform, String client_code,
			Date trade_date, String ib_code, String product_group, String brand_code) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { client_ib_code, platform, client_code, trade_date, ib_code, product_group, brand_code };
		List<IbCommissionClientSummaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionClientSummaryBean>(IbCommissionClientSummaryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbCommissionClientSummary(String client_ib_code, String platform, String client_code, Date trade_date, String ib_code,
			String product_group, String brand_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { client_ib_code, platform, client_code, trade_date, ib_code, product_group, brand_code };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteIbCommissionClientSummaryByDateRangeIbCodes(Date start_date, Date end_date, List<String> ib_Codes) throws Exception {
		String sql = map.get("deleteByDateRange");

		sql += " AND ib_code in ('" + StringUtils.join(ib_Codes, "','") + "')";
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { startDateString, endDateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int deleteIbCommissionClientSummaryByDateRange(Date start_date, Date end_date) throws Exception {
		String sql = map.get("deleteByDateRange");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { startDateString, endDateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}	

	@Override
	public void generateIbCommissionClientSummarys(Date startDate, Date endDate) throws Exception {
		final String sql = map.get("generateClientSummary");
		Object[] objs = new Object[] { startDate, endDate };
		this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public List<IbClientCommissionSummaryWebModel> getIbClientCommissionSummaryByIbCodeDate(String ib_code, Date start_date, Date end_date)
			throws Exception {
		final String sql = map.get("selectByIbCodeDateRange");

		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { startDateString, endDateString, ib_code };
		List<IbClientCommissionSummaryWebModel> list = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbClientCommissionSummaryWebModel>(IbClientCommissionSummaryWebModel.class), objs);
		return list;
	}
	@Override
	public List<IbCommissionClientSummaryBean> getIbCommissionClientSummaryByIbCodeClientIbCode(String ib_code,
			String client_ib_code, Date startDate, Date endDate) throws Exception {
		final String sql = map.get("selectByIbCodeClientIbCode");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[]{client_ib_code, ib_code, startDateString, endDateString};
		List<IbCommissionClientSummaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionClientSummaryBean>(IbCommissionClientSummaryBean.class), objs);
		return beans;
	}
	@Override
	public List<IbClientSummariesModel> getIbClientSummary(String ibCode, Date startDate, Date endDate)
			throws Exception {
		final String sql = map.get("selectIbClientSummary");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[]{ibCode, startDateString, endDateString, endDateString, startDateString, endDateString, ibCode, startDateString, endDateString};
		List<IbClientSummariesModel> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientSummariesModel>(IbClientSummariesModel.class), objs);
		return beans;
	}
	
	@Override
	public List<IbCommissionModel> getIbCommissionSummary(String ibCode, Date startDate, Date endDate)
			throws Exception {
		final String sql = map.get("selectIbCommissionSummary");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[]{ibCode, startDateString, endDateString, endDateString,
				startDateString, endDateString,
				ibCode, startDateString, endDateString, endDateString,
				startDateString, endDateString};
		List<IbCommissionModel> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionModel>(IbCommissionModel.class), objs);
		return beans;
	}
	
	@Override
	public List<IbCommissionDetailsModel> getIbCommissionSummaryDetails(String ibCode, Date startDate, Date endDate)
			throws Exception {
		final String sql = map.get("selectIbCommissionSummaryDetail");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		Object[] objs = new Object[]{startDateString, endDateString, ibCode,
				startDateString, endDateString, ibCode, ibCode};
		List<IbCommissionDetailsModel> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionDetailsModel>(IbCommissionDetailsModel.class), objs);
		return beans;
	}

	@Override
	public List<IbCommissionClientSummaryBean> getIbCommissionClientSummaryByBrandCodeIbCodeDateRange(Date startDate,
			Date endDate, String brand_code, List<String> ib_codes, String user_code) throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectByBrandCodeDateRange");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		
		Object[] objs = new Object[]{startDateString, endDateString, brand_code};
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<IbCommissionClientSummaryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionClientSummaryBean>(IbCommissionClientSummaryBean.class), objs);
		return beans;
	}
	
	public List<ClientSummaryPeriodDto> getIbCommissionClientPeriodSummary(Date startDate, 
			Date endDate, String brand_code, List<String> ib_codes, String user_code) throws Exception{
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectByBrandCodeDateRangePeriod");
		
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);
		
		Object[] objs = new Object[]{startDateString, endDateString, brand_code, startDateString, endDateString, startDateString, endDateString};
		
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<ClientSummaryPeriodDto> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientSummaryPeriodDto>(ClientSummaryPeriodDto.class), objs);
		return beans;
	}

	
}
