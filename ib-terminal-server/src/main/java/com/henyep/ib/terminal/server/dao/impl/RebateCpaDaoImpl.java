package com.henyep.ib.terminal.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.RebateCpaBean;
import com.henyep.ib.terminal.server.dao.RebateCpaDao;
import com.henyep.ib.terminal.server.dto.dao.IbRebateCpaDto;

@Repository(value = "RebateCpaDao")
public class RebateCpaDaoImpl implements RebateCpaDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "RebateCpaDao_SQLMap")
	Map<String, String> map;

	public RebateCpaDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveRebateCpa(RebateCpaBean rebateCpa) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { rebateCpa.getRebate_code(), rebateCpa.getCurrency(), rebateCpa.getAmount(), rebateCpa.getMin_lot(),
				rebateCpa.getMin_deposit(), rebateCpa.getStart_date(), rebateCpa.getEnd_date(), rebateCpa.getLast_update_time(),
				rebateCpa.getLast_update_user() };
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updateRebateCpa(RebateCpaBean rebateCpa) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { rebateCpa.getCurrency(), rebateCpa.getAmount(), rebateCpa.getMin_lot(), rebateCpa.getMin_deposit(),
				rebateCpa.getEnd_date(), rebateCpa.getLast_update_time(), rebateCpa.getLast_update_user(), rebateCpa.getRebate_code(),
				rebateCpa.getStart_date() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RebateCpaBean> getAllRebateCpas() throws Exception {
		String sql = map.get("selectAll");
		List<RebateCpaBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateCpaBean>(RebateCpaBean.class));
		return beans;
	}

	@Override
	public List<RebateCpaBean> getRebateCpaByKey(String rebate_code, Date start_date) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { rebate_code, start_date };
		List<RebateCpaBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateCpaBean>(RebateCpaBean.class), objs);
		return beans;
	}

	@Override
	public int deleteRebateCpa(String rebate_code, Date start_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { rebate_code, start_date };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RebateCpaBean> getRebateCpasByDateRange(Date start_date, Date end_date) throws Exception {
		final String sql = map.get("selectByDateRange");
		Object[] objs = new Object[] { start_date, end_date };
		List<RebateCpaBean> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateCpaBean>(RebateCpaBean.class), objs);
		return list;
	}

	@Override
	public List<IbRebateCpaDto> getIbRebateCpaByDateRange(Date start_date, Date end_date) throws Exception {
		final String sql = map.get("selectByIbCpaByDateRange");
		Object[] objs = new Object[] { start_date,start_date, end_date,end_date };
		List<IbRebateCpaDto> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbRebateCpaDto>(IbRebateCpaDto.class), objs);
		return list;
	}

}
