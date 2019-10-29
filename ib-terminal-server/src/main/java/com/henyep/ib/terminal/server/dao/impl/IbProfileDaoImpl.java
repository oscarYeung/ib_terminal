package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.server.dao.IbProfileDao;

@Repository(value = "IbProfileDao")
public class IbProfileDaoImpl implements IbProfileDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbProfileDao_SQLMap")
	Map<String, String> map;

	public IbProfileDaoImpl() throws Exception {
		super();
	}

	@Override
	public IbProfileBean getProfileByIbCodeAndPassword(String ibCode, String password) throws Exception {
		final String sql = map.get("getProfileByIbCodeAndPassword");
		Object[] objs = new Object[] { ibCode, password };
		List<IbProfileBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class), objs);
		if (beans != null && beans.size() == 1)
			return beans.get(0);

		return null;
	}

	@Override
	public void saveIbProfile(IbProfileBean ibProfile) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibProfile.getIb_code(), ibProfile.getAddress(), ibProfile.getBrand_code(), ibProfile.getChinese_name(),
				ibProfile.getCountry(), ibProfile.getEmail(), ibProfile.getFirst_name(), ibProfile.getLanguage(), ibProfile.getLast_name(),
				ibProfile.getMobile(), ibProfile.getPassword(), ibProfile.getSex(), ibProfile.getStatus(), ibProfile.getUsername(), ibProfile.getLast_update_user(),
				ibProfile.getIs_white_label_user()};
		 
		int res = this.jdbcTemplate.update(sql, objs);
	}

	@Override
	public int updateIbProfile(IbProfileBean ibProfile) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibProfile.getSex(), ibProfile.getStatus(), ibProfile.getPassword(), ibProfile.getChinese_name(),
				ibProfile.getCountry(), ibProfile.getFirst_name(), ibProfile.getUsername(), ibProfile.getEmail(), ibProfile.getAddress(),
				ibProfile.getLast_name(), ibProfile.getLast_update_user(), ibProfile.getLanguage(), ibProfile.getMobile(), ibProfile.getBrand_code(),
				ibProfile.getIb_code() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;

	}

	@Override
	public List<IbProfileBean> getAllIbProfiles() throws Exception {
		String sql = map.get("selectAll");
		List<IbProfileBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class));
		return beans;
	}

	@Override
	public IbProfileBean getIbProfileByKey(String ib_code) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { ib_code };
		List<IbProfileBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class), objs);
		if (beans != null && beans.size() == 1)
			return beans.get(0);

		return null;
	}

	@Override
	public int deleteIbProfile(int ib_code, String brand_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { ib_code, brand_code };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbProfileBean> getIbProfileByIbCode(String ib_code) throws Exception {
		final String sql = map.get("selectByIbCode");
		Object[] objs = new Object[] { ib_code };
		List<IbProfileBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class), objs);
		return beans;
	}
}
