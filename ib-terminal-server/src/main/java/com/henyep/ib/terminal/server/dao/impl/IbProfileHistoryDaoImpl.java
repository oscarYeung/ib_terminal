package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbProfileHistoryBean;
import com.henyep.ib.terminal.server.dao.IbProfileHistoryDao;

@Repository(value = "IbProfileHistoryDao")
public class IbProfileHistoryDaoImpl implements IbProfileHistoryDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbProfileHistoryDao_SQLMap")
	Map<String, String> map;
	public IbProfileHistoryDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveIbProfileHistory(IbProfileHistoryBean ibProfileHistory) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				ibProfileHistory.getSex(),
				ibProfileHistory.getStatus(),
				ibProfileHistory.getPassword(),
				ibProfileHistory.getBrand_code(),
				ibProfileHistory.getVersion(),
				ibProfileHistory.getChinese_name(),
				ibProfileHistory.getCountry(),
				ibProfileHistory.getFirst_name(),
				ibProfileHistory.getUsername(),
				ibProfileHistory.getEmail(),
				ibProfileHistory.getAddress(),
				ibProfileHistory.getCreate_time(),
				ibProfileHistory.getLast_name(),
				ibProfileHistory.getLast_update_user(),
				ibProfileHistory.getLanguage(),
				ibProfileHistory.getIb_code(),
				ibProfileHistory.getMobile()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateIbProfileHistory(IbProfileHistoryBean ibProfileHistory) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				ibProfileHistory.getSex(),
				ibProfileHistory.getStatus(),
				ibProfileHistory.getPassword(),
				ibProfileHistory.getBrand_code(),
				ibProfileHistory.getVersion(),
				ibProfileHistory.getChinese_name(),
				ibProfileHistory.getCountry(),
				ibProfileHistory.getFirst_name(),
				ibProfileHistory.getUsername(),
				ibProfileHistory.getEmail(),
				ibProfileHistory.getAddress(),
				ibProfileHistory.getCreate_time(),
				ibProfileHistory.getLast_name(),
				ibProfileHistory.getLast_update_user(),
				ibProfileHistory.getLanguage(),
				ibProfileHistory.getIb_code(),
				ibProfileHistory.getMobile(),
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbProfileHistoryBean> getAllIbProfileHistorys() throws Exception{
		String sql = map.get("selectAll");
		List<IbProfileHistoryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbProfileHistoryBean>(IbProfileHistoryBean.class));
		return beans;
	}

	@Override
	public List<IbProfileHistoryBean> getIbProfileHistoryByKey() throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{};
		List<IbProfileHistoryBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbProfileHistoryBean>(IbProfileHistoryBean.class), objs);
		return beans;
	}

	@Override
	public int deleteIbProfileHistory() throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
}
