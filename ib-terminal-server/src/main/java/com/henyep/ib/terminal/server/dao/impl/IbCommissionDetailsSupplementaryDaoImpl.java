package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbCommissionDetailsSupplementaryBean;
import com.henyep.ib.terminal.server.dao.IbCommissionDetailsSupplementaryDao;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbCommissionDetailsSupplementaryDao")
public class IbCommissionDetailsSupplementaryDaoImpl implements IbCommissionDetailsSupplementaryDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbCommissionDetailsSupplementaryDao_SQLMap")
	Map<String, String> map;

	public IbCommissionDetailsSupplementaryDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveIbCommissionDetailsSupplementary(IbCommissionDetailsSupplementaryBean ibCommissionDetailsSupplementary) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibCommissionDetailsSupplementary.getBrand_code(), ibCommissionDetailsSupplementary.getIb_code(),
				ibCommissionDetailsSupplementary.getGroup_code(), ibCommissionDetailsSupplementary.getTrade_date(),
				ibCommissionDetailsSupplementary.getCurrency(), ibCommissionDetailsSupplementary.getStatus(),
				ibCommissionDetailsSupplementary.getEv_commission(), ibCommissionDetailsSupplementary.getTotal_equity_change(),
				ibCommissionDetailsSupplementary.getClient_fix_commission(), ibCommissionDetailsSupplementary.getClient_rebate_commission(),
				ibCommissionDetailsSupplementary.getMargin_in_bonus(), ibCommissionDetailsSupplementary.getDeposit_bonus(),
				ibCommissionDetailsSupplementary.getCredit_card_charges(),
				ibCommissionDetailsSupplementary.getDeficit(), ibCommissionDetailsSupplementary.getNet_ev(),
				ibCommissionDetailsSupplementary.getRebate_code(), ibCommissionDetailsSupplementary.getEv_percentage(),
				ibCommissionDetailsSupplementary.getAdjustment(), ibCommissionDetailsSupplementary.getNetMargin(),
				ibCommissionDetailsSupplementary.getLast_update_time(), ibCommissionDetailsSupplementary.getLast_update_user() };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public IbCommissionDetailsSupplementaryBean updateIbCommissionDetailsSupplementary(
			IbCommissionDetailsSupplementaryBean ibCommissionDetailsSupplementary) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibCommissionDetailsSupplementary.getCurrency(), ibCommissionDetailsSupplementary.getStatus(),
				ibCommissionDetailsSupplementary.getEv_commission(), ibCommissionDetailsSupplementary.getTotal_equity_change(),
				ibCommissionDetailsSupplementary.getClient_fix_commission(), ibCommissionDetailsSupplementary.getClient_rebate_commission(),
				ibCommissionDetailsSupplementary.getMargin_in_bonus(), ibCommissionDetailsSupplementary.getDeposit_bonus(),
				ibCommissionDetailsSupplementary.getCredit_card_charges(),
				ibCommissionDetailsSupplementary.getDeficit(), ibCommissionDetailsSupplementary.getNet_ev(),
				ibCommissionDetailsSupplementary.getRebate_code(), ibCommissionDetailsSupplementary.getEv_percentage(),
				ibCommissionDetailsSupplementary.getAdjustment(), ibCommissionDetailsSupplementary.getNetMargin(),
				ibCommissionDetailsSupplementary.getLast_update_time(), ibCommissionDetailsSupplementary.getLast_update_user(),
				ibCommissionDetailsSupplementary.getBrand_code(), ibCommissionDetailsSupplementary.getIb_code(),
				ibCommissionDetailsSupplementary.getTrade_date(), ibCommissionDetailsSupplementary.getGroup_code() };

		int res = this.jdbcTemplate.update(sql, objs);
		if (res > 0)
			return getIbCommissionDetailsSupplementaryByKey(ibCommissionDetailsSupplementary.getIb_code(),
					ibCommissionDetailsSupplementary.getTrade_date(), ibCommissionDetailsSupplementary.getGroup_code());
		else
			return null;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> getAllIbCommissionDetailsSupplementarys() throws Exception {
		String sql = map.get("selectAll");
		List<IbCommissionDetailsSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsSupplementaryBean>(IbCommissionDetailsSupplementaryBean.class));
		return beans;
	}

	@Override
	public IbCommissionDetailsSupplementaryBean getIbCommissionDetailsSupplementaryByKey(String ib_code, Date trade_date, String group_code)
			throws Exception {
		final String sql = map.get("selectByKey");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);

		Object[] objs = new Object[] { ib_code, tradeDateString, group_code };
		List<IbCommissionDetailsSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsSupplementaryBean>(IbCommissionDetailsSupplementaryBean.class), objs);
		return (beans != null && beans.size() == 1) ? beans.get(0) : null;
	}

	@Override
	public int deleteIbCommissionDetailsSupplementary(String brand_code, String ib_code, Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { brand_code, ib_code, trade_date };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> getIbCommissionDetailsSupplementaryByDateRange(Date start_date, Date end_date)
			throws Exception {
		final String sql = map.get("getByDateRange");
		Object[] objs = new Object[] { start_date, end_date };
		List<IbCommissionDetailsSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsSupplementaryBean>(IbCommissionDetailsSupplementaryBean.class), objs);
		return beans;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> getRecordsByDateRangeStatus(Date start_date, Date end_date, String status) throws Exception {

		final String sql = map.get("getRecordsByDateRangeStatus");
		Object[] objs = new Object[] { start_date, end_date, status };
		List<IbCommissionDetailsSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsSupplementaryBean>(IbCommissionDetailsSupplementaryBean.class), objs);
		return beans;

	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> getIbCommissionDetailsSupplementaryByIbCodeAndDate(String ib_code, Date trade_date)
			throws Exception {
		final String sql = map.get("getByIbCodeAndDate");
		Object[] objs = new Object[] { ib_code, trade_date };
		List<IbCommissionDetailsSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsSupplementaryBean>(IbCommissionDetailsSupplementaryBean.class), objs);
		return beans;
	}

	@Override
	public List<IbCommissionDetailsSupplementaryBean> getIbCommissionDetailsSupplementaryByGroupCodeIbCodeAndDateRange(List<String> groups,
			List<String> ibs, Date trade_date) throws Exception {
		String sql = map.get("getByGroupCodeIbCodeAndDate");
		String customCause = " and group_code in ('" + StringUtils.join(groups, "', '") + "')";
		customCause += " and ib_code in ('" + StringUtils.join(ibs, "', '") + "')";

		sql = sql.replace("#CUSTOM#", customCause);
		Object[] objs = new Object[] { trade_date };
		List<IbCommissionDetailsSupplementaryBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbCommissionDetailsSupplementaryBean>(IbCommissionDetailsSupplementaryBean.class), objs);
		return beans;
	}

}
