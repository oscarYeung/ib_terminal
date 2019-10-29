package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.BonusDetailsBean;
import com.henyep.ib.terminal.server.dao.BonusDetailsDao;

@Repository(value = "BonusDetailsDao")
public class BonusDetailsDaoImpl implements BonusDetailsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "BonusDetailsDao_SQLMap")
	Map<String, String> map;
	public BonusDetailsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveBonusDetails(BonusDetailsBean bonusDetails) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				bonusDetails.getBonus_code(),
				bonusDetails.getCurrency(),
				bonusDetails.getBonus_type(),
				bonusDetails.getMin_amount(),
				bonusDetails.getAward_percentage(),
				bonusDetails.getLast_update_time(),
				bonusDetails.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateBonusDetails(BonusDetailsBean bonusDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				bonusDetails.getCurrency(),
				bonusDetails.getMin_amount(),
				bonusDetails.getAward_percentage(),
				bonusDetails.getLast_update_time(),
				bonusDetails.getLast_update_user(),
				bonusDetails.getBonus_code(),
				bonusDetails.getBonus_type()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<BonusDetailsBean> getAllBonusDetailss() throws Exception{
		String sql = map.get("selectAll");
		List<BonusDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<BonusDetailsBean>(BonusDetailsBean.class));
		return beans;
	}

	@Override
	public List<BonusDetailsBean> getBonusDetailsByKey(String bonus_code, String bonus_type) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{bonus_code, bonus_type};
		List<BonusDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<BonusDetailsBean>(BonusDetailsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteBonusDetails(String bonus_code, String bonus_type) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{bonus_code, bonus_type};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
