package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.RebateDetailsBean;
import com.henyep.ib.terminal.api.dto.response.clientPackageDetails.ClientPackageSpreadTypeDto;
import com.henyep.ib.terminal.server.dao.IbTreeDao;
import com.henyep.ib.terminal.server.dao.RebateDetailsDao;
import com.henyep.ib.terminal.server.dto.dao.ReportIbRebateDto;

@Repository(value = "RebateDetailsDao")
public class RebateDetailsDaoImpl implements RebateDetailsDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "RebateDetailsDao_SQLMap")
	Map<String, String> map;

	@Resource(name = "IbTreeDao")
	IbTreeDao ibTreeDao;
	
	public RebateDetailsDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveRebateDetails(RebateDetailsBean rebateDetails) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { 
				rebateDetails.getRebate_code(),
				rebateDetails.getSpread_type(),
				rebateDetails.getProduct_group(),
				rebateDetails.getClient_package_code(),
				rebateDetails.getGroup_code(),
				rebateDetails.getCurrency(),
				rebateDetails.getMin_lot(),
				rebateDetails.getClient_fix_commission_type(),
				rebateDetails.getClient_fix_commission(),
				rebateDetails.getClient_spread_commission_type(),
				rebateDetails.getClient_spread_commission(),
				rebateDetails.getRebate_method(),
				rebateDetails.getRebate_type(),
				rebateDetails.getRebate_commission(),
				rebateDetails.getRebate_to_master_type(),
				rebateDetails.getRebate_to_master(),
				rebateDetails.getStart_date(),
				rebateDetails.getEnd_date(),
				rebateDetails.getLast_update_user()};
		int res = this.jdbcTemplate.update(sql, objs);

		
	}

	@Override
	public void saveRebateDetails(List<RebateDetailsBean> rebateDetails) throws Exception {

		String sql = map.get("create");

		int contentPos = sql.indexOf("(?");
		String insertStatement = sql.substring(0, contentPos);
		String valueContent = sql.substring(contentPos, sql.length());

		List<String> valueContentList = new ArrayList<String>();
		ArrayList<Object> objList = new ArrayList<Object>();

		for (RebateDetailsBean rebateDetail : rebateDetails) {
			Object[] objs = new Object[] {
					rebateDetail.getRebate_code(),
					rebateDetail.getSpread_type(),
					rebateDetail.getProduct_group(),
					rebateDetail.getClient_package_code(),
					rebateDetail.getGroup_code(),
					rebateDetail.getCurrency(),
					rebateDetail.getMin_lot(),
					rebateDetail.getClient_fix_commission_type(),
					rebateDetail.getClient_fix_commission(),
					rebateDetail.getClient_spread_commission_type(),
					rebateDetail.getClient_spread_commission(),
					rebateDetail.getRebate_method() == null? "A": rebateDetail.getRebate_method(),
					rebateDetail.getRebate_type(),
					rebateDetail.getRebate_commission(),
					rebateDetail.getRebate_to_master_type() == null? "$": rebateDetail.getRebate_to_master_type(),
					rebateDetail.getRebate_to_master() == null? 0: rebateDetail.getRebate_to_master(),
					rebateDetail.getStart_date(),
					rebateDetail.getEnd_date(),
					rebateDetail.getLast_update_user() };
			objList.addAll(Arrays.asList(objs));
			valueContentList.add(valueContent);
		}

		sql = insertStatement + StringUtils.join(valueContentList, ",");
		int res = this.jdbcTemplate.update(sql, objList.toArray());
	}

	@Override
	public int updateRebateDetails(RebateDetailsBean rebateDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { 
				rebateDetails.getGroup_code(),
				rebateDetails.getCurrency(),
				rebateDetails.getClient_fix_commission_type(),
				rebateDetails.getClient_fix_commission(),
				rebateDetails.getClient_spread_commission_type(),
				rebateDetails.getClient_spread_commission(),
				rebateDetails.getRebate_method(),
				rebateDetails.getRebate_type(),
				rebateDetails.getRebate_commission(),
				rebateDetails.getRebate_to_master_type(),
				rebateDetails.getRebate_to_master(),
				rebateDetails.getEnd_date(),
				rebateDetails.getLast_update_user(),
				rebateDetails.getRebate_code(),
				rebateDetails.getSpread_type(),
				rebateDetails.getProduct_group(),
				rebateDetails.getClient_package_code(),
				rebateDetails.getMin_lot(),
				rebateDetails.getStart_date()  
				};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int updateRebateDetails(List<RebateDetailsBean> rebateDetails) throws Exception {

		int totalCount = 0;
		for (RebateDetailsBean rebateDetail : rebateDetails) {
			totalCount += updateRebateDetails(rebateDetail);
		}
		return totalCount;

	}

	@Override
	public List<RebateDetailsBean> getAllRebateDetailss() throws Exception {

		String sql = map.get("selectAll");
		List<RebateDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateDetailsBean>(RebateDetailsBean.class));

		return beans;
	}

	@Override
	public int deleteRebateDetails(String rebate_code, String spread_type, double min_lot, String client_package_code, Date start_date, String product_group)
			throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { rebate_code, spread_type, min_lot, client_package_code, start_date, product_group };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int deleteRebateDetails(List<RebateDetailsBean> rebateDetails) throws Exception {
		int totalDeleteCount = 0;
		for (RebateDetailsBean rebateDetail : rebateDetails) {
			totalDeleteCount += deleteRebateDetails(rebateDetail.getRebate_code(), rebateDetail.getSpread_type(), rebateDetail.getMin_lot(), rebateDetail.getClient_package_code(),
					rebateDetail.getStart_date(), rebateDetail.getProduct_group());
		}

		return totalDeleteCount;
	}

	@Override
	public int deleteRebateDetailsByRebateCodeClientPackageCode(String rebateCode, String clientPackageCode, String spreadType) throws Exception {
		final String sql = map.get("deleteByRebateCodeClientPackageCode");
		Object[] objs = new Object[] { rebateCode, clientPackageCode, spreadType };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RebateDetailsBean> getRebateDetailsByKey(String rebate_code, String client_package_code, Date start_date, String product_group)
			throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { rebate_code, client_package_code, start_date, product_group };
		List<RebateDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateDetailsBean>(RebateDetailsBean.class), objs);
		return beans;
	}

	@Override
	public List<RebateDetailsBean> getRebateDetailsByRebateCode(String rebate_code) throws Exception {
		final String sql = map.get("selectByRebateCode");
		Object[] objs = new Object[] { rebate_code };
		List<RebateDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateDetailsBean>(RebateDetailsBean.class), objs);
		return beans;
	}

	@Override
	public List<RebateDetailsBean> getRebateDetailsByRebateCodeWithDateRange(String rebate_code, Date start_date, Date end_date) throws Exception {
		final String sql = map.get("selectByRebateCodeWithDateRange");
		Object[] objs = new Object[] { rebate_code, start_date, end_date };
		List<RebateDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateDetailsBean>(RebateDetailsBean.class), objs);
		return beans;
	}

	@Override
	public List<ClientPackageSpreadTypeDto> getClientPackageCodesByRebateCode(String rebate_code) throws Exception {
		final String sql = map.get("selectClientPackageCodeByRebateCode");
		Object[] objs = new Object[] { rebate_code };
		List<ClientPackageSpreadTypeDto> clientPackageCodes = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientPackageSpreadTypeDto>(ClientPackageSpreadTypeDto.class), objs);
		return clientPackageCodes;

	}

	@Override
	public List<ReportIbRebateDto> getReportByExample(ReportIbRebateDto dto) {
		String sql = map.get("getReportByExample");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		objList.add(dto.getStart_date());
		objList.add(dto.getEnd_date());
					
		if (!StringUtils.isBlank(dto.getIb_code())) {
			whereClauses.add(" ib.ib_code = ?");
			objList.add(dto.getIb_code());
		}
		if (!StringUtils.isBlank(dto.getBrand_code())) {
			whereClauses.add(" r.brand_code = ?");
			objList.add(dto.getBrand_code());
		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" AND ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}
		
		List<ReportIbRebateDto> result = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ReportIbRebateDto>(ReportIbRebateDto.class),
				objList.toArray());
		return result;
	}
	
	@Override
	public List<ReportIbRebateDto> getReportByUserExample(ReportIbRebateDto dto, String userCode, String brandCode) throws Exception{
		
		List<String> ibTeamHeads = ibTreeDao.getIbTeamHeadByUserCode(userCode, brandCode);
		
		String sql = map.get("getReportByUserExample");
		StringBuilder sqlBuilder = new StringBuilder(sql);
		List<String> whereClauses = new ArrayList<String>();
		List<Object> objList = new ArrayList<Object>();
		objList.add(dto.getStart_date());
		objList.add(dto.getEnd_date());
					
		if (!StringUtils.isBlank(dto.getIb_code())) {
			whereClauses.add(" ib.ib_code = ?");
			objList.add(dto.getIb_code());
		}
		if (!StringUtils.isBlank(dto.getBrand_code())) {
			whereClauses.add(" r.brand_code = ?");
			objList.add(dto.getBrand_code());
		}

		if (whereClauses.size() > 0 && objList.size() > 0) {
			sqlBuilder.append(" AND ");
			sqlBuilder.append(StringUtils.join(whereClauses, " AND "));
			sql = sqlBuilder.toString();
		}
		
		sql = sql.replace("#team_head_ids", StringUtils.join(ibTeamHeads, ','));
		
		List<ReportIbRebateDto> result = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ReportIbRebateDto>(ReportIbRebateDto.class),
				objList.toArray());
		return result;
	}

}
