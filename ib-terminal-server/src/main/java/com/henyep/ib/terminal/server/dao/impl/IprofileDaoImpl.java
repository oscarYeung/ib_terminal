package com.henyep.ib.terminal.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.henyep.ib.terminal.api.dto.db.IbProfileBean;
import com.henyep.ib.terminal.api.global.Constants;
import com.henyep.ib.terminal.server.dao.IprofileDao;
import com.henyep.ib.terminal.server.util.TimestampTypeAdapter;

/**
 * IprofileDao:CRUD
 *
 * @author ShenZhong
 * @date 2016年8月17日
 */
@Repository(value = "sz_IprofileDao")
public class IprofileDaoImpl implements IprofileDao
{
	final static Logger log = Logger.getLogger(IprofileDaoImpl.class);
	private Gson g = new GsonBuilder().registerTypeAdapter(java.sql.Timestamp.class, new TimestampTypeAdapter())
			.setDateFormat(Constants.FORMAT_DATETIME).create();
	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;
	@Resource(name = "sz_IprofileDao_SQLMap")
	Map<String, String> map;
	SimpleJdbcCall execProc4Profile;
	@Resource(name = "IbProfileDao_SQLMap")
	Map<String, String> ibProfile_SQLMap;

	@PostConstruct
	private void init()
	{
		execProc4Profile = new SimpleJdbcCall(jdbcTemplate).withProcedureName("create_ib_node_simple");
	}

	@Override
	public int create(IbProfileBean profile) throws Exception
	{
		int res = this.jdbcTemplate.update(ibProfile_SQLMap.get("create"),
				new Object[] { profile.getIb_code(), profile.getAddress(), profile.getBrand_code(), profile.getChinese_name(), profile.getCountry(),
						profile.getEmail(), profile.getFirst_name(), profile.getLanguage(), profile.getLast_name(),
						profile.getMobile(), profile.getPassword(),
						profile.getSex(), profile.getStatus(), profile.getUsername(), profile.getLast_update_user(), profile.getIs_white_label_user() });

		return res;
	}

	@Override
	public IbProfileBean getProfileByUsernameAndPassword(String username, String password) throws Exception
	{
		IbProfileBean pb = this.jdbcTemplate.queryForObject(ibProfile_SQLMap.get("getProfileByUsernameAndPassword"),
				new Object[] { username, password }, new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class));
		return pb;
	}
	
	@Override
	public int updateProfilePassword(String ib_code, String password, String sender) throws Exception
	{
		int res = this.jdbcTemplate.update(ibProfile_SQLMap.get("updateProfilePassword"),
				new Object[] { password, sender, ib_code });
		return res;
	}

	@Override
	public int getProfileCountByUsername(String username) throws Exception
	{
		int i = this.jdbcTemplate.queryForObject(ibProfile_SQLMap.get("getProfileCountByUsername"), new Object[] { username }, Integer.class);
		return i;
	}

	@Override
	public int updateProfile(IbProfileBean profile) throws Exception
	{
		// set user name to ib code
		profile.setUsername(profile.getIb_code());
		
		int res = this.jdbcTemplate.update(ibProfile_SQLMap.get("update"),

				new Object[] {
						profile.getSex(),
						profile.getStatus(),
						profile.getPassword(),
						profile.getChinese_name(),
						profile.getCountry(),
						profile.getFirst_name(),
						profile.getUsername(),
						profile.getEmail(), 
						profile.getAddress(), 
						profile.getLast_name(),
						profile.getLast_update_user(),
						profile.getLanguage(),
						profile.getMobile(), 
						profile.getBrand_code(),
						profile.getIb_code()
						});
		return res;
		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("ib_code", profile.getIb_code());
//		return this.commonUpdateBeanByAndParamsMap(profile, paramMap, IbProfileBean.class, "ib_profile");
	}

	private <T> int commonUpdateBeanByAndParamsMap(T entity, Map<String, Object> paramMap, Class<T> clazz, String tableName)
	{
		String json = g.toJson(entity, clazz);
		Map<String, Object> map = g.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
		List<Object> setList = new java.util.ArrayList<Object>();
		for (String param : map.keySet())
		{
			setList.add(param + "=?");
		}
		StringBuffer sql = new StringBuffer();
		List<Object> values = new java.util.ArrayList<Object>(map.values());
		sql.append("update ").append(tableName != null && !tableName.trim().isEmpty() ? tableName : clazz.getSimpleName()).append(" set ")
				.append(StringUtils.collectionToDelimitedString(setList, ","));
		boolean isHaveParams = paramMap != null && !paramMap.isEmpty();
		if (isHaveParams)
		{
			List<Object> paramList = new java.util.ArrayList<Object>();
			sql.append(" where ");
			for (String paramName : paramMap.keySet())
			{
				paramList.add(paramName + "=?");
			}
			sql.append(StringUtils.collectionToDelimitedString(paramList, " and "));
			values.addAll(paramMap.values());
		}
		log.info("updateEntity:sql[" + sql.toString() + "],values[" + g.toJson(values) + "]");
		return this.jdbcTemplate.update(sql.toString(), values.toArray());
	}

	@Override
	public IbProfileBean getProfileByIbCode(String ibCode) throws Exception
	{
		return this.jdbcTemplate.queryForObject(ibProfile_SQLMap.get("getProfileByIbCode"), new Object[] { ibCode },
				new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class));
	}

	@Override
	public IbProfileBean getProfileByUsernameAndBrandCode(String username, String brandCode) throws Exception
	{
		return this.jdbcTemplate.queryForObject(ibProfile_SQLMap.get("getProfileByUsernameAndBrandCode"), new Object[] { username, brandCode },
				new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class));
	}

	@Override
	public void execProc(String procName, Object... os)
	{
		SqlParameterSource in = new MapSqlParameterSource().addValue("parent_ib_code", os[0]).addValue("child_ib_code", os[1]);
		this.execProc4Profile.execute(in);
	}

	@Override
	public IbProfileBean getProfileByUsernameAndEmail(String username, String email) throws Exception
	{
		return this.jdbcTemplate.queryForObject(ibProfile_SQLMap.get("getProfileByUsernameAndEmail"), new Object[] { username, email },
				new BeanPropertyRowMapper<IbProfileBean>(IbProfileBean.class));
	}
}
