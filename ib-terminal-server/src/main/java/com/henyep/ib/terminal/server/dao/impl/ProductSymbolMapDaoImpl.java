package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ProductSymbolMapBean;
import com.henyep.ib.terminal.server.dao.ProductSymbolMapDao;

@Repository(value = "ProductSymbolMapDao")
public class ProductSymbolMapDaoImpl implements ProductSymbolMapDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "ProductSymbolMapDao_SQLMap")
	Map<String, String> map;
	public ProductSymbolMapDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveProductSymbolMap(ProductSymbolMapBean productSymbolMap) throws Exception {
		final String sql = map.get("create");
		Object[] objs = new Object[]{
				productSymbolMap.getLast_update_time(),
				productSymbolMap.getEnd_date(),
				productSymbolMap.getSymbol(),
				productSymbolMap.getLast_update_user(),
				productSymbolMap.getStart_date(),
				productSymbolMap.getProduct_group(),
				productSymbolMap.getPriority()
		};
		int res = this.jdbcTemplate.update(sql, objs);

	}


	@Override
	public int updateProductSymbolMap(ProductSymbolMapBean productSymbolMap) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				productSymbolMap.getLast_update_time(),
				productSymbolMap.getEnd_date(),
				productSymbolMap.getLast_update_user(),
				productSymbolMap.getPriority(),
				productSymbolMap.getSymbol(),
				productSymbolMap.getStart_date(),
				productSymbolMap.getProduct_group()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}

	@Override
	public List<ProductSymbolMapBean> getAllProductSymbolMaps() throws Exception{
		String sql = map.get("selectAll");
		List<ProductSymbolMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ProductSymbolMapBean>(ProductSymbolMapBean.class));
		return beans;
	}

	@Override
	public List<ProductSymbolMapBean> getProductSymbolMapByKey(String symbol, Date start_date, String product_group) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{symbol, start_date, product_group};
		List<ProductSymbolMapBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<ProductSymbolMapBean>(ProductSymbolMapBean.class), objs);
		return beans;
	}

	@Override
	public int deleteProductSymbolMap(String symbol, Date start_date, String product_group) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{symbol, start_date, product_group};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public List<String> getCurrentProductGroupList(String sender, String spread_type) throws Exception {
		final String sql = map.get("selectCurrentProductGroupList");
		Object[] objs = new Object[]{sender, spread_type};
		List<String> productGroups = this.jdbcTemplate.queryForList(sql, String.class, objs);
		return productGroups;
	}
}
