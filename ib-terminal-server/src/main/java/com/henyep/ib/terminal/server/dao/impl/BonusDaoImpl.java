package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.BonusBean;
import com.henyep.ib.terminal.server.dao.BonusDao;

@Repository(value = "BonusDao")
public class BonusDaoImpl implements BonusDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "BonusDao_SQLMap")
	Map<String, String> map;
	public BonusDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveBonus(BonusBean bonus) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				bonus.getId(),
				bonus.getBrand_code(),
				bonus.getBonus_code(),
				bonus.getDescription(),
				bonus.getLast_update_time(),
				bonus.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateBonus(BonusBean bonus) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				bonus.getBrand_code(),
				bonus.getBonus_code(),
				bonus.getDescription(),
				bonus.getLast_update_time(),
				bonus.getLast_update_user(),
				bonus.getId()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<BonusBean> getAllBonuss() throws Exception{
		String sql = map.get("selectAll");
		List<BonusBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<BonusBean>(BonusBean.class));
		return beans;
	}

	@Override
	public List<BonusBean> getBonusByKey(Integer id) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{id};
		List<BonusBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<BonusBean>(BonusBean.class), objs);
		return beans;
	}

	@Override
	public int deleteBonus(Integer id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{id};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
