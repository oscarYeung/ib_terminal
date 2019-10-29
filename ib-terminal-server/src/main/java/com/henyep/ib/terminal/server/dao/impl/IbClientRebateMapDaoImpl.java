package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbClientRebateMapBean;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.server.dao.IbClientRebateMapDao;
import com.henyep.ib.terminal.server.dto.dao.IbRebateGroupCodeDto;

@Repository(value = "IbClientRebateMapDao")
public class IbClientRebateMapDaoImpl implements IbClientRebateMapDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbClientRebateMapDao_SQLMap")
	Map<String, String> map;

	public IbClientRebateMapDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveIbClientRebateMap(IbProfileBean ibProfileBean, Date startDate, Date endDate) throws Exception {
		final String sql = map.get("create");

		String ibCode = ibProfileBean.getIb_code();
		String brandCode = ibProfileBean.getBrand_code();
		String clientCode = "*";
		String rebateCode = "R" + ibCode;
		String lastUpdateUser = ibProfileBean.getLast_update_user();

		Object[] objs = new Object[] { brandCode, ibCode, clientCode, rebateCode, startDate, endDate, lastUpdateUser};
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public void saveIbClientRebateMap(IbClientRebateMapBean ibClientRebateMap) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibClientRebateMap.getBrand_code(), ibClientRebateMap.getIb_code(), ibClientRebateMap.getClient_code(),
				ibClientRebateMap.getRebate_code(), ibClientRebateMap.getStart_date(), ibClientRebateMap.getEnd_date(), ibClientRebateMap.getLast_update_user()};
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updateIbClientRebateMap(IbClientRebateMapBean ibClientRebateMap) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibClientRebateMap.getLast_update_time(), ibClientRebateMap.getEnd_date(), ibClientRebateMap.getRebate_code(),
				ibClientRebateMap.getLast_update_user(), ibClientRebateMap.getClient_code(), ibClientRebateMap.getIb_code(),
				ibClientRebateMap.getStart_date(), ibClientRebateMap.getBrand_code() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbClientRebateMapBean> getAllIbClientRebateMaps() throws Exception {
		String sql = map.get("selectAll");
		List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class));
		return beans;
	}

	@Override
	public List<IbClientRebateMapBean> getIbClientRebateMapByKey(String client_code, String ib_code, Date start_date, String brand_code)
			throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { client_code, ib_code, start_date, brand_code };
		List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbClientRebateMap(String client_code, String ib_code, Date start_date, String brand_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { client_code, ib_code, start_date, brand_code };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbClientRebateMapBean> getIbClientRebateMapByIbCodeWithDateRange(String brand_code, String ib_code, Date start_date, Date end_date)
			throws Exception {
		final String sql = map.get("selectByIbCodeWithDateRange");
		Object[] objs = new Object[] { brand_code, ib_code, start_date, end_date };
		List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class), objs);
		return beans;
	}

	@Override
	public List<IbClientRebateMapBean> getIbClientRebateMapByRebateCodeWithDateRange(String rebate_code, Date start_date, Date end_date)
			throws Exception {
		final String sql = map.get("selectByRebateCodeWithDateRange");
		Object[] objs = new Object[] { rebate_code, start_date, end_date };
		List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class), objs);
		return beans;
	}

	@Override
	public List<IbClientRebateMapBean> getByRebateCodeList(List<String> rebateCodeList) throws Exception {
		String sql = map.get("getByRebateCodeList");
		if (rebateCodeList != null && rebateCodeList.size() > 0) {
			String rebateCodes = " ('" + StringUtils.join(rebateCodeList, "', '") + "')";
			sql = sql.replace("#CUSTOM#", rebateCodes);
			List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class));
			return beans;

		}

		return null;
	}

	@Override
	public List<IbClientRebateMapBean> getByRebateCodeList(List<String> rebateCodeList, Date start_date, Date end_date) throws Exception {
		String sql = map.get("getByRebateCodeListWithDateRange");
		if (rebateCodeList != null && rebateCodeList.size() > 0) {
			String rebateCodes = " ('" + StringUtils.join(rebateCodeList, "', '") + "')";
			sql = sql.replace("#CUSTOM#", rebateCodes);
			Object[] objs = new Object[] { start_date, end_date };
			List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
					new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class), objs);
			return beans;

		}

		return null;
	}

	@Override
	public List<IbClientRebateMapBean> getByIbCode(String ibCode) throws Exception {
		final String sql = map.get("getByIbCode");
		Object[] objs = new Object[] { ibCode };
		List<IbClientRebateMapBean> beans = this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<IbClientRebateMapBean>(IbClientRebateMapBean.class), objs);
		return beans;
	}

	@Override
	public List<IbRebateGroupCodeDto> getIbRebateGroupCodeList(Date start_date, Date end_date) throws Exception {
		final String sql = map.get("getIbRebateGroupCodeList");
		Object[] objs = new Object[] { start_date, end_date, start_date, end_date };
		List<IbRebateGroupCodeDto> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbRebateGroupCodeDto>(IbRebateGroupCodeDto.class),
				objs);
		return beans;
	}

}
