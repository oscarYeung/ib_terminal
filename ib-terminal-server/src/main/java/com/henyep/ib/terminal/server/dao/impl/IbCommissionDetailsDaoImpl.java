package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsBean;
import com.henyep.ib.terminal.api.dto.response.ibcommission.details.IbCommissionDetailsWebModel;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dto.report.ClientMarginInOutReportDto;
import com.henyep.ib.terminal.server.dto.report.IbMarginInOutReportDto;
import com.henyep.ib.terminal.server.dto.report.IbRebateReportDto;
import com.henyep.ib.terminal.server.dto.report.IbTradeAmountReportDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbCommissionDetailsDao")
public class IbCommissionDetailsDaoImpl implements IbCommissionDetailsDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbCommissionDetailsDao_SQLMap")
	Map<String, String> map;

	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;

	public IbCommissionDetailsDaoImpl() throws Exception {
		super();
	}

	@Override
	public int updateIbCommissionDetails(IbCommissionDetailsBean ibCommissionDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibCommissionDetails.getRebate_per_lot(), ibCommissionDetails.getClient_ib_code(),
				ibCommissionDetails.getGroup_code(), ibCommissionDetails.getBuy_sell(), ibCommissionDetails.getLast_update_time(),
				ibCommissionDetails.getRebate_per_pip(), ibCommissionDetails.getSymbol(), ibCommissionDetails.getRebate_code(),
				ibCommissionDetails.getTrade_pl(), ibCommissionDetails.getClient_spread_commission(), ibCommissionDetails.getRebate_type_pip(),
				ibCommissionDetails.getProduct_group(), ibCommissionDetails.getSpread_type(), ibCommissionDetails.getJurisdiction(), 
				ibCommissionDetails.getCurrency(), ibCommissionDetails.getDeposit(),
				ibCommissionDetails.getRebate_commission_pip(), ibCommissionDetails.getRebate_type_lot(), ibCommissionDetails.getTrade_swaps(),
				ibCommissionDetails.getTrade_date(), ibCommissionDetails.getClose_trade_time(), ibCommissionDetails.getClient_fix_commission(),
				ibCommissionDetails.getLot(), ibCommissionDetails.getRebate_commission_lot(), ibCommissionDetails.getLast_update_user(),
				ibCommissionDetails.getOpen_trade_time(), ibCommissionDetails.getPlatform(), ibCommissionDetails.getClient_code(),
				ibCommissionDetails.getTicket(), ibCommissionDetails.getIb_code(), ibCommissionDetails.getBrand_code() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbCommissionDetailsBean> getAllIbCommissionDetailss() throws Exception {
		String sql = map.get("selectAll");
		List<IbCommissionDetailsBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsBean>(IbCommissionDetailsBean.class));
		return beans;
	}

	@Override
	public List<IbCommissionDetailsBean> getIbCommissionDetailsByKey(String platform, String client_code, String ticket, String ib_code,
			String brand_code) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { platform, client_code, ticket, ib_code, brand_code };
		List<IbCommissionDetailsBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsBean>(IbCommissionDetailsBean.class), objs);
		return beans;
	}

	// INSERT INTO ib_commission_details
	// (client_ib_code, rebate_per_lot, rebate_per_pip, rebate_code,
	// product_group, currency, rebate_commission_pip, rebate_type_lot,
	// trade_swaps, ticket, rebate_commission_lot, last_update_user, buy_sell,
	// platform, last_update_time, symbol, trade_pl, client_spread_commission,
	// rebate_type_pip, brand_code, client_code, trade_date, close_trade_time,
	// client_fix_commission, lot, ib_code, open_trade_time)
	// VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,
	// ?, ?, ?, ?, ?)

	@Override
	public void saveIbCommissionDetails(List<IbCommissionDetailsBean> ibCommissionDetails) throws Exception {
		if (ibCommissionDetails != null && ibCommissionDetails.size() > 0) {
			final String sql = map.get("create");
			String[] snipples = sql.split("VALUES ");
			String insertSql = snipples[0];
			String valuesSql = snipples[1];

			List<String> valuesSqls = new ArrayList<String>();
			List<Object> objList = new ArrayList<Object>();
			int totalCount = ibCommissionDetails.size();

			for (IbCommissionDetailsBean ibCommissionDetail : ibCommissionDetails) {

				Object[] objs = new Object[] {

						ibCommissionDetail.getBrand_code(), ibCommissionDetail.getPlatform(), ibCommissionDetail.getTicket(),
						ibCommissionDetail.getIb_code(), ibCommissionDetail.getClient_code(), ibCommissionDetail.getClient_ib_code(),
						ibCommissionDetail.getGroup_code(), ibCommissionDetail.getProduct_group(), 
						ibCommissionDetail.getSpread_type(), ibCommissionDetail.getJurisdiction(),
						ibCommissionDetail.getTrade_date(),
						ibCommissionDetail.getSymbol(), ibCommissionDetail.getBuy_sell(), ibCommissionDetail.getLot(),
						ibCommissionDetail.getCurrency(), ibCommissionDetail.getDeposit(), ibCommissionDetail.getClient_fix_commission(),
						ibCommissionDetail.getClient_spread_commission(), ibCommissionDetail.getRebate_commission_lot(),
						ibCommissionDetail.getRebate_commission_pip(), ibCommissionDetail.getTrade_swaps(), ibCommissionDetail.getTrade_pl(),
						ibCommissionDetail.getOpen_trade_time(), ibCommissionDetail.getClose_trade_time(), ibCommissionDetail.getRebate_code(),
						ibCommissionDetail.getRebate_type_lot(), ibCommissionDetail.getRebate_per_lot(), ibCommissionDetail.getRebate_type_pip(),
						ibCommissionDetail.getRebate_per_pip(), ibCommissionDetail.getLast_update_time(), ibCommissionDetail.getLast_update_user()

				};
				objList.addAll(Arrays.asList(objs));
				valuesSqls.add(valuesSql);
				totalCount -= 1;
				if (valuesSqls.size() == 1000 || totalCount == 0) {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append(insertSql);
					stringBuilder.append("VALUES ");
					stringBuilder.append(StringUtils.join(valuesSqls, ","));
					int res = this.jdbcTemplate.update(stringBuilder.toString(), objList.toArray());
					objList.clear();
					valuesSqls.clear();
				}
			}
		}
	}

	@Override
	public int deleteIbCommissionDetails(Date startDate, Date endDate) throws Exception {
		final String sql = map.get("deleteByDateRange");

		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);

		Object[] objs = new Object[] { startDateString, endDateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int deleteIbCommissionDetails(Date startDate, Date endDate, List<String> ibCodes) throws Exception {
		String sql = map.get("deleteByDateRange");

		sql += " AND ib_code in ('" + StringUtils.join(ibCodes, "','") + "')";

		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate);

		Object[] objs = new Object[] { startDateString, endDateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbCommissionDetailsWebModel> getIbCommissionDetailsByDateRange(String ib_code, String client_code, Date start_date, Date end_date)
			throws Exception {
		final String sql = map.get("selectByIbCodeClientCodeDateRange");

		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);

		Object[] objs = new Object[] { ib_code, client_code, startDateString, endDateString, };
		List<IbCommissionDetailsWebModel> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsWebModel>(IbCommissionDetailsWebModel.class), objs);
		return beans;
	}

	@Override
	public List<IbCommissionDetailsBean> getSummaryByIbCodeListDateRange(List<String> ibCodeList, Date start_date, Date end_date) throws Exception {

		final String sql = map.get("getSummaryByIbCodeDateRange");
		if (ibCodeList != null && ibCodeList.size() > 0) {
			String ibCodeStringList = StringUtils.join(ibCodeList, ",");
			String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
			String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
			Object[] objs = new Object[] { ibCodeStringList, startDateString, endDateString, };
			List<IbCommissionDetailsBean> list = this.jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<IbCommissionDetailsBean>(IbCommissionDetailsBean.class), objs);
			return list;
		}

		return null;
	}

	@Override
	public List<IbCommissionDetailsBean> getSummaryByBrandCodeIbCodeDateRange(Date start_date, Date end_date, String brand_code, List<String> ib_codes, String user_code)
			throws Exception {
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);
		
		String sql = map.get("selectByBrandCodeIbCode");
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		Object[] objs = new Object[] { startDateString, endDateString, brand_code };
		
		if(ib_codes == null || ib_codes.size() == 0){
			sql = StringUtils.replace(sql, "AND ib_code in (#ibCodes)", "");
		}
		else{
			sql = StringUtils.replace(sql, "#ibCodes", "'" + StringUtils.join(ib_codes, "','") + "'");
		}
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<IbCommissionDetailsBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsBean>(IbCommissionDetailsBean.class), objs);
		return beans;
	}

	@Override
	public List<IbCommissionDetailsBean> getIbCommissionSummaryWithGroupCode(List<String> ibCodeList, Date start_date, Date end_date) {

		String sql = map.get("getSummaryWithGroupCodeByIbCodeDateRange");
		String ibCodeCause = " and ib_code in ('" + StringUtils.join(ibCodeList, "', '") + "')";
		sql = sql.replace("#CUSTOM#", ibCodeCause);
		Object[] objs = new Object[] { start_date, end_date };
		List<IbCommissionDetailsBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsBean>(IbCommissionDetailsBean.class), objs);
		return beans;
	}
	
	
	@Override
	public List<IbRebateReportDto> getIbRebateReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code) {

		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		String ibCodesString = "";
		if(ib_codes != null && ib_codes.size() > 0){
			ibCodesString = StringUtils.join(ib_codes, ",").trim();
		}
		
		String sql = map.get("getIbRebateReport");
		Object[] objs = new Object[] { user, startDateString, endDateString, ibCodesString, brand_code};
		List<IbRebateReportDto> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbRebateReportDto>(IbRebateReportDto.class), objs);
		return beans;
	}
	
	@Override
	public List<IbTradeAmountReportDto> getIbTradeAccountReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code) {
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		String ibCodesString = "";
		if(ib_codes != null && ib_codes.size() > 0){
			ibCodesString = StringUtils.join(ib_codes, ",").trim();
		}
		
		String sql = map.get("getIbTradeAmountReport");
		Object[] objs = new Object[] { user, startDateString, endDateString, ibCodesString, brand_code};
		List<IbTradeAmountReportDto> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbTradeAmountReportDto>(IbTradeAmountReportDto.class), objs);
		return beans;
	}
	
	@Override
	public List<IbMarginInOutReportDto> getIbMarginInOutReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code) {
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		String ibCodesString = "";
		if(ib_codes != null && ib_codes.size() > 0){
			ibCodesString = StringUtils.join(ib_codes, ",").trim();
		}
		
		String sql = map.get("getIbMarginInOutReport");
		Object[] objs = new Object[] { user, startDateString, endDateString, ibCodesString, brand_code};
		List<IbMarginInOutReportDto> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbMarginInOutReportDto>(IbMarginInOutReportDto.class), objs);
		return beans;
	}
	
	@Override
	public List<ClientMarginInOutReportDto> getClientMarginInOutReport(List<String> ib_codes, String user, Date start_date, Date end_date, String brand_code) {
		String startDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		String endDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, end_date);
		
		
		String ibCodesString = "";
		if(ib_codes != null && ib_codes.size() > 0){
			ibCodesString = StringUtils.join(ib_codes, ",").trim();
		}
		
		String sql = map.get("getClientMarginInOutReport");
		Object[] objs = new Object[] { user, startDateString, endDateString, ibCodesString, brand_code};
		List<ClientMarginInOutReportDto> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<ClientMarginInOutReportDto>(ClientMarginInOutReportDto.class), objs);
		return beans;
	}
	
}
