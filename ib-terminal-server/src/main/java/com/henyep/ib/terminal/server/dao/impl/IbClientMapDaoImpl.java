package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbClientMapBean;
import com.henyep.ib.terminal.server.dao.IbClientMapDao;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dto.dao.IbClientMapDto;
import com.henyep.ib.terminal.server.dto.dao.ReportIbClientMapDto;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "IbClientMapDao")
public class IbClientMapDaoImpl implements IbClientMapDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;

	@Resource(name = "IbClientMapDao_SQLMap")
	Map<String, String> map;

	@Resource(name = "sapJdbcTemplate_SapSource")
	JdbcTemplate sapJdbcTemplate;

	public IbClientMapDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveIbClientMap(IbClientMapBean ibClientMap) throws Exception {
		final String sql = map.get("create");

		String endDate = null;
		if (ibClientMap.getEnd_date() != null)
			endDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, ibClientMap.getEnd_date());
		String startDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, ibClientMap.getStart_date());

		Object[] objs = new Object[] { ibClientMap.getIb_code(), ibClientMap.getClient_code(), ibClientMap.getClient_package_code(), startDate,
				endDate, ibClientMap.getLast_update_user() };
		int res = this.jdbcTemplate.update(sql, objs);
	}

	@Override
	public void saveIbClientMap(List<IbClientMapBean> ibClientMaps) throws Exception {

		int batchSize = 3000;
		List<List<IbClientMapBean>> beanBatchList = new ArrayList<List<IbClientMapBean>>();

		while (ibClientMaps.size() > batchSize) {
			List<IbClientMapBean> subList = ibClientMaps.subList(0, batchSize);
			beanBatchList.add(subList);
			ibClientMaps = ibClientMaps.subList(batchSize, ibClientMaps.size());

		}
		if (ibClientMaps.size() > 0) {
			beanBatchList.add(ibClientMaps);
		}

		for (List<IbClientMapBean> beanList : beanBatchList) {

			String sql = map.get("create");

			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());

			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();

			for (IbClientMapBean ibClientMap : beanList) {
				String endDate = null;
				if (ibClientMap.getEnd_date() != null)
					endDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, ibClientMap.getEnd_date());
				String startDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, ibClientMap.getStart_date());

				Object[] objs = new Object[] { ibClientMap.getIb_code(), ibClientMap.getClient_code(), ibClientMap.getClient_package_code(),
						startDate, endDate, ibClientMap.getLast_update_user() };
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}

			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}

	@Override
	public int updateIbClientMap(IbClientMapBean ibClientMap) throws Exception {
		final String sql = map.get("update");

		String endDate = null;
		if (ibClientMap.getEnd_date() != null)
			endDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, ibClientMap.getEnd_date());
		String startDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, ibClientMap.getStart_date());

		Object[] objs = new Object[] { endDate, ibClientMap.getClient_package_code(), ibClientMap.getLast_update_user(), ibClientMap.getClient_code(),
				ibClientMap.getIb_code(), startDate };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbClientMapBean> getAllIbClientMaps() throws Exception {
		String sql = map.get("selectAll");
		List<IbClientMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class));
		return beans;
	}

	@Override
	public List<IbClientMapBean> getIbClientMapByKey(String ib_code, String client_code, Date start_date) throws Exception {
		final String sql = map.get("selectByKey");
		String startDate = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, start_date);
		Object[] objs = new Object[] { client_code, ib_code, startDate };
		List<IbClientMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbClientMap(String client_code, String ib_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { client_code, ib_code };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int deleteIbClientMapByTradeDate(Date trade_date) throws Exception {
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		final String sql = map.get("deleteByTradeDate");
		Object[] objs = new Object[] { tradeDateString };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<String> getIbClientMapByIbCode(String ib_code) throws Exception {

		ArrayList<String> ibClientMaps = new ArrayList<String>();
		final String sql = map.get("selectByIbCode");
		Object[] objs = new Object[] { ib_code };
		List<IbClientMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class), objs);

		for (IbClientMapBean bean : beans) {
			ibClientMaps.add(bean.getClient_code());
		}
		return ibClientMaps;
	}

	@Override
	public List<IbClientMapBean> getIbClientMapByIbCodeClientCode(String ib_code, String client_code) throws Exception {

		ArrayList<String> ibClientMaps = new ArrayList<String>();
		final String sql = map.get("selectByIbCodeClientCode");
		Object[] objs = new Object[] { ib_code, client_code };
		List<IbClientMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class), objs);

		return beans;
	}

	@Override
	public List<IbClientMapBean> getIbClientMapByClientCodes(List<Integer> clientCodes) throws Exception {

		List<IbClientMapBean> beans = new ArrayList<IbClientMapBean>();

		if (clientCodes.size() > 0) {
			String sql = map.get("selectAll");

			sql += " WHERE client_code in (" + StringUtils.join(clientCodes, ", ") + ");";
			beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class));
		}

		return beans;
	}

	@Override
	public List<IbClientMapBean> getIbClientMapBeanByIbCode(String ib_code) throws Exception {

		ArrayList<IbClientMapBean> ibClientMaps = new ArrayList<IbClientMapBean>();
		final String sql = map.get("selectByIbCode");
		Object[] objs = new Object[] { ib_code };
		List<IbClientMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class), objs);

		for (IbClientMapBean bean : beans) {
			ibClientMaps.add(bean);
		}
		return ibClientMaps;
	}

	@Override
	public List<IbClientMapBean> getByIbCodeDateRange(String ib_code, Date trade_date) throws Exception {
		final String sql = map.get("selectByIbCodeDateRange");
		Object[] objs = new Object[] { ib_code, trade_date, trade_date };
		return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class), objs);

	}

	@Override
	public List<String> getClientCodesByIbTeamHeads(List<String> ib_team_heads) throws Exception {
		String sql = map.get("getClientCodes");
		sql += "where ib_code in (select ib_code from ib_tree where team in (";

		sql += "'" + StringUtils.join(ib_team_heads, "', '") + "'";

		sql += "));";
		List<String> cltCodes = this.jdbcTemplate.queryForList(sql, String.class);
		return cltCodes;

	}

	@Override
	public List<IbClientMapDto> getIbClientMapByCreateDate(Date createDate) throws Exception {

		String sql = map.get("SAP_selectByCreateDate");
		String createDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, createDate);
		Object[] objs = new Object[] { createDateString, createDateString };
		// Object[] objs = new Object[] {};
		List<IbClientMapDto> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapDto>(IbClientMapDto.class), objs);
		
		return beans;
	}

	@Override
	public List<ReportIbClientMapDto> getReportByExample(ReportIbClientMapDto dto) throws Exception {
		String sql = map.get("getReportByExample");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		objList.add(dto.getStart_date());
		objList.add(dto.getEnd_date());

		if (!StringUtils.isBlank(dto.getIb_code())) {
			whereClauses.add(" m.ib_code = ?");
			objList.add(dto.getIb_code());
		}

		if (!StringUtils.isBlank(dto.getBrand_code())) {
			whereClauses.add(" p.brand_code = ?");
			objList.add(dto.getBrand_code());
		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" AND ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}

		sql += " ORDER BY p.brand_code, m.ib_code, m.client_code";

		List<ReportIbClientMapDto> result = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ReportIbClientMapDto>(ReportIbClientMapDto.class),
				objList.toArray());
		return result;
	}

	@Override
	public List<ReportIbClientMapDto> getReportByUserExample(ReportIbClientMapDto dto, String user_code, String brand_code) throws Exception {
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(user_code, brand_code);

		String sql = map.get("getReportByUserExample");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		objList.add(dto.getStart_date());
		objList.add(dto.getEnd_date());

		if (!StringUtils.isBlank(dto.getIb_code())) {
			whereClauses.add(" m.ib_code = ?");
			objList.add(dto.getIb_code());
		}

		if (!StringUtils.isBlank(dto.getBrand_code())) {
			whereClauses.add(" p.brand_code = ?");
			objList.add(dto.getBrand_code());
		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" AND ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}

		sql += " ORDER BY p.brand_code, m.ib_code, m.client_code";

		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));

		List<ReportIbClientMapDto> result = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ReportIbClientMapDto>(ReportIbClientMapDto.class),
				objList.toArray());
		return result;
	}

	@Override
	public Integer getClientCountByIbCodeDateRange(String ib_code, Date trade_date) {
		final String sql = map.get("selectCountByIbCodeDateRange");
		Object[] objs = new Object[] { ib_code, trade_date, trade_date };
		return this.jdbcTemplate.queryForObject(sql, objs, Integer.class);
	}

	@Override
	public Map<String, List<IbClientMapBean>> getIbClientListByIbCodes(List<String> ibCodeList, List<String> excludedClientList, Date start_date,
			Date end_date) throws Exception {
		String sql = map.get("getIbClientListByIbCodes");

		if (ibCodeList != null && ibCodeList.size() > 0) {
			String ibCodes = "'" + StringUtils.join(ibCodeList, "', '") + "'";
			sql += " and ib_code in (" + ibCodes + ")";
		}

		if (excludedClientList != null && excludedClientList.size() > 0) {
			String excludedClients = "'" + StringUtils.join(excludedClientList, "', '") + "'";
			sql += " and client_code not in (" + excludedClients + ")";
		}

		Object[] objs = new Object[] { start_date, end_date };
		List<IbClientMapBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientMapBean>(IbClientMapBean.class), objs);
		if (list != null && list.size() > 0) {
			HashMap<String, List<IbClientMapBean>> map = new HashMap<String, List<IbClientMapBean>>();
			for (IbClientMapBean obj : list) {
				if (!map.containsKey(obj.getIb_code())) {
					map.put(obj.getIb_code(), new ArrayList<IbClientMapBean>());
				} 
				List<IbClientMapBean> clientList = map.get(obj.getIb_code());
				clientList.add(obj);			
			}
			return map;
		}
		return null;
	}

}
