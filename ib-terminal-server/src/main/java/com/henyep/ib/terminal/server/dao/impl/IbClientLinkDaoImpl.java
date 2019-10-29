package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.IbClientLinkBean;
import com.henyep.ib.terminal.server.dao.IbClientLinkDao;

@Repository(value = "IbClientLinkDao")
public class IbClientLinkDaoImpl implements IbClientLinkDao {

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "IbClientLinkDao_SQLMap")
	Map<String, String> map;

	public IbClientLinkDaoImpl() throws Exception {
		super();
	}

	@Override
	public int saveIbClientLink(IbClientLinkBean ibClientLink) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[] { ibClientLink.getClient_package_code(), ibClientLink.getLast_update_user(), ibClientLink.getIb_code(),
				ibClientLink.getUrl(), ibClientLink.getCampaign_id(), ibClientLink.getBrand_code(), ibClientLink.getDescription(),
				ibClientLink.getSeq_id() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public int updateIbClientLink(IbClientLinkBean ibClientLink) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[] { ibClientLink.getLast_update_user(), ibClientLink.getBrand_code(), ibClientLink.getDescription(),
				ibClientLink.getSeq_id(), ibClientLink.getClient_package_code(), ibClientLink.getIb_code(), ibClientLink.getUrl(),
				ibClientLink.getCampaign_id() };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbClientLinkBean> getAllIbClientLinks() throws Exception {
		String sql = map.get("selectAll");
		List<IbClientLinkBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientLinkBean>(IbClientLinkBean.class));
		return beans;
	}

	@Override
	public IbClientLinkBean getIbClientLinkByKey(String client_package_code, String ib_code, String url, String campaign_id) throws Exception {
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[] { client_package_code, ib_code, url, campaign_id };
		IbClientLinkBean beans = null;
		try {
			beans = this.jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<IbClientLinkBean>(IbClientLinkBean.class), objs);
		} catch (Exception e) {

		}
		return beans;
	}

	@Override
	public int deleteIbClientLink(String client_package_code, String ib_code, String url, String campaign_id) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[] { client_package_code, ib_code, url, campaign_id };
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<IbClientLinkBean> getByBrandCodeAndIbCode(String brand_code, String ib_code) {
		final String sql = map.get("selectByBrandCodeAndIbCode");
		Object[] objs = new Object[] { brand_code, ib_code };
		List<IbClientLinkBean> res = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<IbClientLinkBean>(IbClientLinkBean.class), objs);
		return res;
	}

	@Override
	public int getMaxSeqId(String brand_code, String ib_code) {
		final String sql = map.get("getMaxSeqId");
		Object[] objs = new Object[] { brand_code, ib_code };
		Integer seq_id = this.jdbcTemplate.queryForObject(sql, Integer.class, objs);
		return seq_id;
	}
}
