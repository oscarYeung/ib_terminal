package com.henyep.ib.terminal.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbCommissionCpaBean;
import com.henyep.ib.terminal.server.dao.IbCommissionCpaDao;

@Repository(value = "IbCommissionCpaDao")
public class IbCommissionCpaDaoImpl implements IbCommissionCpaDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbCommissionCpaDao_SQLMap")
	Map<String, String> map;

	public IbCommissionCpaDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveIbCommissionCpa(IbCommissionCpaBean ibCommissionCpa) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibCommissionCpa.getBrand_code(), ibCommissionCpa.getIb_code(), ibCommissionCpa.getClient_code(),
				ibCommissionCpa.getTrade_date(), ibCommissionCpa.getCurrency(), ibCommissionCpa.getTotal_lot(), ibCommissionCpa.getTotal_deposit(),
				ibCommissionCpa.getAmount(), ibCommissionCpa.getMin_lot(), ibCommissionCpa.getMin_deposit(), ibCommissionCpa.getLast_update_time(),
				ibCommissionCpa.getLast_update_user() };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updateIbCommissionCpa(IbCommissionCpaBean ibCommissionCpa) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibCommissionCpa.getTrade_date(), ibCommissionCpa.getCurrency(), ibCommissionCpa.getTotal_lot(),
				ibCommissionCpa.getTotal_deposit(), ibCommissionCpa.getAmount(), ibCommissionCpa.getMin_lot(), ibCommissionCpa.getMin_deposit(),
				ibCommissionCpa.getLast_update_time(), ibCommissionCpa.getLast_update_user(), ibCommissionCpa.getBrand_code(),
				ibCommissionCpa.getIb_code(), ibCommissionCpa.getClient_code() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbCommissionCpaBean> getAllIbCommissionCpas() throws Exception {
		String sql = map.get("selectAll");
		List<IbCommissionCpaBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionCpaBean>(IbCommissionCpaBean.class));
		return beans;
	}

	@Override
	public List<IbCommissionCpaBean> getIbCommissionCpaByKey(String brand_code, String ib_code, String client_code) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { brand_code, ib_code, client_code };
		List<IbCommissionCpaBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionCpaBean>(IbCommissionCpaBean.class),
				objs);
		return beans;
	}

	@Override
	public int deleteIbCommissionCpa(String brand_code, String ib_code, String client_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { brand_code, ib_code, client_code };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<String> getClientListByIbList(List<String> ibList) throws Exception {
		String sql = map.get("selectAll");
		String ibCodes = "'" + StringUtils.join(ibList, "', '") + "'";
		sql += " Where ib_code in (" + ibCodes + ")";
		List<IbCommissionCpaBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbCommissionCpaBean>(IbCommissionCpaBean.class));
		if (beans != null && beans.size() > 0) {
			List<String> clientList = new ArrayList<String>();
			for (IbCommissionCpaBean obj : beans) {
				if (!clientList.contains(obj.getClient_code())) {
					clientList.add(obj.getClient_code());
				}
			}
			return clientList;
		}

		return null;
	}

	@Override
	public int deleteIbCommissionCpa(Date tradeDate) throws Exception {
		final String sql = map.get("deleteByDate");
		Object[] objs = new Object[] { tradeDate };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
