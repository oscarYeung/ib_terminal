package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientPackageDetailsBean;
import com.henyep.ib.terminal.server.dao.ClientPackageDetailsDao;

@Repository(value = "ClientPackageDetailsDao")
public class ClientPackageDetailsDaoImpl implements ClientPackageDetailsDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "ClientPackageDetailsDao_SQLMap")
	Map<String, String> map;
	public ClientPackageDetailsDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveClientPackageDetails(ClientPackageDetailsBean clientPackageDetails) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				clientPackageDetails.getClient_package_code(),
				clientPackageDetails.getBase_commission()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateClientPackageDetails(ClientPackageDetailsBean clientPackageDetails) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				clientPackageDetails.getBase_commission(),
				clientPackageDetails.getClient_package_code()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ClientPackageDetailsBean> getAllClientPackageDetailss() throws Exception{
		String sql = map.get("selectAll");
		List<ClientPackageDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientPackageDetailsBean>(ClientPackageDetailsBean.class));
		return beans;
	}

	@Override
	public List<ClientPackageDetailsBean> getClientPackageDetailsByKey(String client_package_code) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{client_package_code};
		List<ClientPackageDetailsBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ClientPackageDetailsBean>(ClientPackageDetailsBean.class), objs);
		return beans;
	}

	@Override
	public int deleteClientPackageDetails(String client_package_code) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{client_package_code};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	
}
