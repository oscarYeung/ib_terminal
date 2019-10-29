package com.henyep.ib.terminal.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.RebateBean;
import com.henyep.ib.terminal.server.dao.RebateDao;

@Repository(value = "RebateDao")
public class RebateDaoImpl implements RebateDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "RebateDao_SQLMap")
	Map<String, String> map;

	public RebateDaoImpl() throws Exception {
		super();
	}

	@Override
	public void saveRebate(RebateBean rebate) throws Exception {
		final String sql = map.get("create");
		
		Boolean inputIgnoreTreeRebate = rebate.getIgnore_tree_rebate();
		int ignoreTreeRebate = 0;
		if(inputIgnoreTreeRebate != null && inputIgnoreTreeRebate){
			ignoreTreeRebate = 1;
		}
		
		Object[] objs = new Object[] {  
				rebate.getRebate_code(),
				rebate.getBrand_code(),
				ignoreTreeRebate,
				rebate.getDescription(),
				rebate.getLast_update_user()
				};
		int res = this.jdbcTemplate.update(sql, objs);

	}

	@Override
	public int updateRebate(RebateBean rebate) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { rebate.getDescription(), rebate.getLast_update_user(), rebate.getBrand_code(), rebate.getIgnore_tree_rebate(),
				rebate.getId() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<RebateBean> getAllRebates() throws Exception {
		String sql = map.get("selectAll");
		List<RebateBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<RebateBean>(RebateBean.class));
		return beans;
	}

	@Override
	public RebateBean getRebateByKey(int id) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { id };
		RebateBean bean = (RebateBean) this.jdbcTemplate.query(sql,
				new BeanPropertyRowMapper<RebateBean>(RebateBean.class), objs);
		return bean;
	}

	@Override
	public int deleteRebate(int id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { id };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public RebateBean getRebateByRebateCode(String rebate_code) throws Exception {
		final String sql = map.get("selectByRebateCode");
		Object[] objs = new Object[] { rebate_code };
		RebateBean bean;
		try {
			bean = this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<RebateBean>(RebateBean.class), objs);
		} catch (EmptyResultDataAccessException e) {
			bean = null;
		}
		return bean;
	}

	@Override
	public RebateBean getRebateByIbCodeWithDateRange(String ib_code, Date start_date, Date end_date) throws Exception {
		final String sql = map.get("selectByIbCodeWithDateRange");
		Object[] objs = new Object[] { ib_code, start_date, end_date };
		RebateBean bean = this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<RebateBean>(RebateBean.class),
					objs);
		return bean;
	}
}
