package com.henyep.ib.terminal.server.dao.impl;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.server.dao.IprofileHistoryDao;

@Repository(value = "sz_IprofileHistoryDao")
public class IprofileHistoryDaoImpl implements IprofileHistoryDao
{
	final static Logger log = Logger.getLogger(IprofileHistoryDaoImpl.class);
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	@Resource(name = "IbProfileHistoryDao_SQLMap")
	Map<String, String> map;

	@Override
	public int create(IbProfileBean profile) throws Exception
	{
		int version = this.jdbcTemplate.queryForObject(map.get("selectMaxVersionByCode"), new Object[] { profile.getIb_code() },
				Integer.class);
		int res = this.jdbcTemplate.update(map.get("create"),
				new Object[] { profile.getAddress(), profile.getBrand_code(), profile.getChinese_name(), profile.getCountry(),
						profile.getCreate_time(), profile.getEmail(), profile.getFirst_name(), profile.getLanguage(), profile.getLast_name(),
						// profile.getLast_update_time(),
						profile.getLast_update_user(), profile.getMobile(), profile.getPassword(), profile.getSex(), profile.getStatus(),
						profile.getUsername(), profile.getIb_code(), version });
		return res;
	}
}
