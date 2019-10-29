package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbBonusMapBean;
import com.henyep.ib.terminal.server.dao.IbBonusMapDao;

@Repository(value = "IbBonusMapDao")
public class IbBonusMapDaoImpl implements IbBonusMapDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbBonusMapDao_SQLMap")
	Map<String, String> map;
	public IbBonusMapDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveIbBonusMap(IbBonusMapBean ibBonusMap) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				ibBonusMap.getBrand_code(),
				ibBonusMap.getIb_code(),
				ibBonusMap.getBonus_code(),
				ibBonusMap.getStart_date(),
				ibBonusMap.getEnd_date(),
				ibBonusMap.getLast_update_time(),
				ibBonusMap.getLast_update_user()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateIbBonusMap(IbBonusMapBean ibBonusMap) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				ibBonusMap.getEnd_date(),
				ibBonusMap.getLast_update_time(),
				ibBonusMap.getLast_update_user(),
				ibBonusMap.getBrand_code(),
				ibBonusMap.getIb_code(),
				ibBonusMap.getBonus_code(),
				ibBonusMap.getStart_date()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbBonusMapBean> getAllIbBonusMaps() throws Exception{
		String sql = map.get("selectAll");
		List<IbBonusMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbBonusMapBean>(IbBonusMapBean.class));
		return beans;
	}

	@Override
	public List<IbBonusMapBean> getIbBonusMapByKey(String brand_code, String ib_code, String bonus_code, Date start_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{brand_code, ib_code, bonus_code, start_date};
		List<IbBonusMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbBonusMapBean>(IbBonusMapBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbBonusMap(String brand_code, String ib_code, String bonus_code, Date start_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{brand_code, ib_code, bonus_code, start_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
