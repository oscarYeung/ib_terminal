package com.henyep.ib.terminal.server.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.MarginOutBean;
import com.henyep.ib.terminal.api.dto.request.marginout.MaxWithdrawalModel;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.MarginOutDao;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "MarginOutDao")
public class MarginOutDaoImpl implements MarginOutDao {

	final static Logger log = Logger.getLogger(MarginOutDaoImpl.class);
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;

	@Resource(name = "MarginOutDao_SQLMap")
	Map<String, String> map;

	public MarginOutDaoImpl() throws Exception {
		super();
	}

	@Override
	public int saveMarginOut(final MarginOutBean marginOut) throws Exception {
		final String sql = map.get("create");
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
				ps.setString(1, marginOut.getBrand_code());
				ps.setString(2, marginOut.getCategory());
				ps.setString(3, marginOut.getMethod());
				ps.setString(4, marginOut.getAccount());
				ps.setString(5, marginOut.getCurrency());
				ps.setDouble(6, marginOut.getAmount());
				ps.setString(7, marginOut.getAccount_currency());
				ps.setDouble(8, marginOut.getExchange_rate());
				ps.setDouble(9, marginOut.getAccount_amount());
				if (marginOut.getTrade_date() != null)
					ps.setTimestamp(10, new Timestamp(marginOut.getTrade_date().getTime()));
				else
					ps.setTimestamp(10, null);
				ps.setString(11, marginOut.getStatus());
				ps.setString(12, marginOut.getBatch_file_id());
				ps.setString(13, marginOut.getComment());
				ps.setString(14, marginOut.getCreate_user());
				ps.setString(15, marginOut.getLast_update_user());

				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}

	@Override
	public void saveMarginOuts(List<MarginOutBean> marginOuts) throws Exception {
		int batchSize = 3000;
		List<List<MarginOutBean>> beanBatchList = new ArrayList<List<MarginOutBean>>();

		while (marginOuts.size() > batchSize) {
			List<MarginOutBean> subList = marginOuts.subList(0, batchSize);
			beanBatchList.add(subList);
			marginOuts = marginOuts.subList(batchSize, marginOuts.size());

		}
		if (marginOuts.size() > 0) {
			beanBatchList.add(marginOuts);
		}

		for (List<MarginOutBean> beanList : beanBatchList) {

			String sql = map.get("create");

			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());

			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();

			for (MarginOutBean marginOut : beanList) {

				String tradeDate = null;
				if (marginOut.getTrade_date() != null) {
					tradeDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, marginOut.getTrade_date());
				}

				Object[] objs = new Object[] { marginOut.getBrand_code(), marginOut.getCategory(), marginOut.getMethod(), marginOut.getAccount(),
						marginOut.getCurrency(), marginOut.getAmount(), marginOut.getAccount_currency(), marginOut.getExchange_rate(),
						marginOut.getAccount_amount(), tradeDate, marginOut.getStatus(), marginOut.getBatch_file_id(), marginOut.getComment(),
						marginOut.getCreate_user(), marginOut.getLast_update_user() };
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}

	@Override
	public int updateMarginOut(MarginOutBean marginOut) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { marginOut.getBrand_code(), marginOut.getCategory(), marginOut.getMethod(), marginOut.getAccount(),
				marginOut.getCurrency(), marginOut.getAmount(), marginOut.getAccount_currency(), marginOut.getExchange_rate(),
				marginOut.getAccount_amount(), marginOut.getTrade_date(), marginOut.getStatus(), marginOut.getBatch_file_id(), marginOut.getComment(),
				marginOut.getLast_update_user(), marginOut.getId() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<MarginOutBean> getAllMarginOuts() throws Exception {
		String sql = map.get("selectAll");
		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class));
		return beans;
	}

	@Override
	public List<MarginOutBean> getMarginOutsByBatchFileId(String batchFileId) throws Exception {
		String sql = map.get("selectByBatchFileId");
		Object[] objs = new Object[] { batchFileId };
		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class), objs);
		return beans;
	}

	@Override
	public List<MarginOutBean> getMarginOutByKey(int id) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { id };
		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class), objs);
		return beans;
	}

	@Override
	public int deleteMarginOut(int id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { id };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<MarginOutBean> getMarginOut(Date startDate, Date endDate, MarginOutBean searchBean) throws Exception {

		String sql = map.get("selectAll");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();

		if (startDate != null) {
			whereClauses.add("trade_date >= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate));
		}
		if (endDate != null) {
			whereClauses.add("trade_date <= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate));
		}

		if (searchBean != null) {
			if (searchBean.getStatus() != null) {
				whereClauses.add("status = ?");
				objList.add(searchBean.getStatus());
			}

			if (searchBean.getAccount() != null) {
				whereClauses.add("account = ?");
				objList.add(searchBean.getAccount());
			}

			if (searchBean.getBrand_code() != null) {
				whereClauses.add("brand_code = ?");
				objList.add(searchBean.getBrand_code());
			}

			if (searchBean.getId() != null) {
				whereClauses.add("id = ?");
				objList.add(searchBean.getId());
			}

			if (searchBean.getMethod() != null) {
				whereClauses.add("method = ?");
				objList.add(searchBean.getMethod());
			}

			if (searchBean.getBatch_file_id() != null) {
				whereClauses.add("batch_file_id = ?");
				objList.add(searchBean.getBatch_file_id());
			}
		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" WHERE ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}
		sql += " ORDER BY last_update_time DESC, trade_date DESC, account DESC";

		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class), objList.toArray());
		return beans;
	}

	@Override
	public List<MarginOutBean> getMarginOutBySalesExample(Date startDate, Date endDate, MarginOutBean searchBean, String userCode, String brandCode)
			throws Exception {

		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(userCode, brandCode);

		String sql = map.get("selectByUserCode");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();

		if (startDate != null) {
			whereClauses.add("trade_date >= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate));
		}
		if (endDate != null) {
			whereClauses.add("trade_date <= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate));
		}

		if (searchBean != null) {
			if (searchBean.getStatus() != null) {
				whereClauses.add("status = ?");
				objList.add(searchBean.getStatus());
			}

			if (searchBean.getAccount() != null) {
				whereClauses.add("account = ?");
				objList.add(searchBean.getAccount());
			}

			if (searchBean.getBrand_code() != null) {
				whereClauses.add("brand_code = ?");
				objList.add(searchBean.getBrand_code());
			}

			if (searchBean.getId() != null) {
				whereClauses.add("id = ?");
				objList.add(searchBean.getId());
			}

		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" AND ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}
		sql += " ORDER BY trade_date DESC, account DESC";

		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));

		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class), objList.toArray());
		return beans;
	}

	@Override
	public List<MarginOutBean> getMarginOutByIbCodeStatus(String ibCode, String status) throws Exception {
		final String sql = map.get("selectByIbCodeStatus");
		Object[] objs = new Object[] { ibCode, status };
		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class), objs);
		return beans;
	}

	@Override
	public MaxWithdrawalModel getMaxWithdrawal(String ib_code) throws Exception {

		final String sql = map.get("getMaxWithdrawal");
		Object[] objs = new Object[] { ib_code, ib_code };

		try {
			MaxWithdrawalModel bean = this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<MaxWithdrawalModel>(MaxWithdrawalModel.class),
					objs);
			return bean;
		} catch (EmptyResultDataAccessException e) {

			return null;
		}

	}

	@Override
	public List<MaxWithdrawalModel> getAllMaxWithdrawal() throws Exception {

		final String sql = map.get("getAllMaxWithdrawal");
		Object[] objs = new Object[] {};

		List<MaxWithdrawalModel> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MaxWithdrawalModel>(MaxWithdrawalModel.class), objs);
		return beans;

	}

	@Override
	public List<MarginOutBean> getMarginOutByIds(List<String> ids) throws Exception {
		if (ids.size() > 0) {
			String sql = map.get("selectAll");
			String idsString = "'" + StringUtils.join(ids, "','") + "'";
			sql += " WHERE id in (" + idsString + ")";
			List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class));
			return beans;
		} else {
			return new ArrayList<MarginOutBean>();
		}
	}

	@Override
	public List<MarginOutBean> getMarginOutByExample(Date startDate, Date endDate, MarginOutBean marginOutBean) throws Exception {

		String sql = map.get("selectAll");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();

		if (startDate != null) {
			whereClauses.add("trade_date >= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, startDate));
		}

		if (endDate != null) {
			whereClauses.add("trade_date <= ?");
			objList.add(DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, endDate));
		}

		if (marginOutBean != null) {
			if (marginOutBean.getStatus() != null) {
				whereClauses.add("status = ?");
				objList.add(marginOutBean.getStatus());
			}

			if (marginOutBean.getAccount() != null) {
				whereClauses.add("account = ?");
				objList.add(marginOutBean.getAccount());
			}

			if (marginOutBean.getBrand_code() != null) {
				whereClauses.add("brand_code = ?");
				objList.add(marginOutBean.getBrand_code());
			}

			if (marginOutBean.getMethod() != null) {
				whereClauses.add("method = ?");
				objList.add(marginOutBean.getMethod());
			}

			if (marginOutBean.getCategory() != null) {
				whereClauses.add("category = ?");
				objList.add(marginOutBean.getCategory());
			}

			if (marginOutBean.getId() != null) {
				whereClauses.add("id = ?");
				objList.add(marginOutBean.getId());
			}

			if (marginOutBean.getComment() != null) {
				whereClauses.add("comment = ?");
				objList.add(marginOutBean.getComment());
			}

		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" WHERE ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}

		sql += " ORDER BY trade_date DESC, account DESC";

		log.info(sql);
		List<MarginOutBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<MarginOutBean>(MarginOutBean.class), objList.toArray());

		return beans;

	}

}
