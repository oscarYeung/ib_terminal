package com.henyep.ib.terminal.server.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.ClientTradeDetailsBean;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.server.dao.SettingsSymbolDao;
import com.henyep.ib.terminal.server.dto.mt4.model.MT5SymbolRecord;
import com.henyep.ib.terminal.server.dto.mt4.model.SymbolRecord;
import com.henyep.ib.terminal.server.util.DateUtil;

@Repository(value = "SettingsSymbolDao")
public class SettingsSymbolDaoImpl implements SettingsSymbolDao{

	@Resource(name = "jdbcTemplate")
	JdbcTemplate jdbcTemplate;

	@Resource(name = "sapJdbcTemplate")
	JdbcTemplate sapJdbcTemplate;
	
	@Resource(name = "SettingsSymbolDao_SQLMap")
	Map<String, String> map;
	public SettingsSymbolDaoImpl() throws Exception{
		super();
	}
	@Override
	public void saveSettingsSymbol(List<SettingsSymbolBean> settingsSymbols, Date trade_date) throws Exception {
		
		if(settingsSymbols != null && settingsSymbols.size() > 0){
			String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
			String sql = map.get("create");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(SettingsSymbolBean settingsSymbol : settingsSymbols){
				Object[] objs = new Object[]{
						settingsSymbol.getSymbol(),
						settingsSymbol.getCurrency(),
						settingsSymbol.getTick_size(),
						settingsSymbol.getContract_size(),
						settingsSymbol.getSpread(),
						settingsSymbol.getClosing_rate(),
						settingsSymbol.getBase_currency_rate(),
						tradeDateString
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}


	@Override
	public int updateSettingsSymbol(SettingsSymbolBean settingsSymbol) throws Exception {
		final String sql = map.get("update");
		Object[] objs = new Object[]{
				settingsSymbol.getCurrency(),
				settingsSymbol.getTick_size(),
				settingsSymbol.getContract_size(),
				settingsSymbol.getSpread(),
				settingsSymbol.getClosing_rate(),
				settingsSymbol.getBase_currency_rate(),
				settingsSymbol.getLast_update_time(),
				settingsSymbol.getLast_update_user(),
				settingsSymbol.getSymbol(),
				settingsSymbol.getTrade_date()
		};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public int updateSettingsSymbolSpreads(List<SymbolRecord> symbolRecords, Date trade_date, String last_udpate_user) throws Exception {
		
		int res = 0;
		if(symbolRecords != null && symbolRecords.size() > 0){
			String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
			String sql = map.get("updateBatch");
			
			int contentPos = sql.indexOf("(?");
			int contentEndPos = sql.indexOf("?)") + 2;
			
			String valueContent = sql.substring(contentPos, contentEndPos);
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(SymbolRecord symbolRecord : symbolRecords){
				Object[] objs = new Object[]{
						symbolRecord.getSymbol(),
						tradeDateString,
						symbolRecord.getSpread(),
						last_udpate_user
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = sql.replace(valueContent, StringUtils.join(valueContentList, ","));
			res = this.jdbcTemplate.update(sql, objList.toArray());
		}
		return res;
		
	}

	@Override
	public List<SettingsSymbolBean> getAllSettingsSymbols(Date trade_date) throws Exception{
		String sql = map.get("selectByTradeDate");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		
		Object[] objs = new Object[]{tradeDateString};
		List<SettingsSymbolBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsSymbolBean>(SettingsSymbolBean.class), objs);
		return beans;
	}

	@Override
	public List<SettingsSymbolBean> getSettingsSymbolByKey(String symbol, Date trade_date) throws Exception{
		final String sql = map.get("selectByKey");
		Object[] objs = new Object[]{symbol, trade_date};
		List<SettingsSymbolBean> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsSymbolBean>(SettingsSymbolBean.class), objs);
		return beans;
	}

	@Override
	public int deleteSettingsSymbol(String symbol, Date trade_date) throws Exception {
		final String sql = map.get("delete");
		Object[] objs = new Object[]{symbol, trade_date};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	@Override
	public SettingsSymbolBean getSettingsSymbolBySymbolTradeDate(String symbol, Date trade_date)
			throws Exception {
		
		List<SettingsSymbolBean> beans = getSettingsSymbolByKey(symbol, trade_date);
		if(beans.size() > 0)
			return beans.get(0);
		else
			return null;
	}
	
	@Override
	public List<SettingsSymbolBean> getSettingsSymbolFromSAP(Date trade_date) throws Exception{
		final String sql = map.get("selectFromSAP");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE_SHORT, trade_date);
		Object[] objs = new Object[]{tradeDateString, tradeDateString, tradeDateString, tradeDateString};
		List<SettingsSymbolBean> beans = this.sapJdbcTemplate.query(sql, new BeanPropertyRowMapper<SettingsSymbolBean>(SettingsSymbolBean.class), objs);
		
		for(SettingsSymbolBean bean : beans){
			if(bean.getBase_currency_rate() == null){
				bean.setBase_currency_rate(1.0);
			}
		}
		
		return beans;
	}
	

	@Override
	public int deleteByTradeDate(Date trade_date) throws Exception{
		final String sql = map.get("deleteByTradeDate");
		String tradeDateString = DateUtil.dateToStringByFormat(DateUtil.FORMATTER_DATE, trade_date);
		Object[] objs = new Object[]{tradeDateString};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	
	// default spread
	@Override
	public List<SymbolRecord> getAllDefaultSpreads() throws Exception{
		final String sql = map.get("selectDefaultSpread");
		Object[] objs = new Object[]{};
		List<SymbolRecord> beans = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<SymbolRecord>(SymbolRecord.class), objs);
		return beans;
	}
	
	@Override
	public int deleteDefaultSpreads() throws Exception{
		final String sql = map.get("deleteDefaultSpread");
		Object[] objs = new Object[]{};
		int res = this.jdbcTemplate.update(sql, objs);
		return res;
	}
	
	@Override
	public void saveMT5Spread(List<MT5SymbolRecord> defaultSpreads) throws Exception {
		
		if(defaultSpreads != null && defaultSpreads.size() > 0){
			String sql = map.get("createDefaultSpread");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(MT5SymbolRecord defaultSpread : defaultSpreads){
				Object[] objs = new Object[]{
						defaultSpread.getSymbol(),
						defaultSpread.getSpread(),
						Math.pow(10, -1 * defaultSpread.getDigits()),
						defaultSpread.getContractSize()
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}
	
	@Override
	public void saveDefaultSpread(List<SymbolRecord> defaultSpreads) throws Exception {
		
		if(defaultSpreads != null && defaultSpreads.size() > 0){
			String sql = map.get("createDefaultSpread");
			
			int contentPos = sql.indexOf("(?");
			String insertStatement = sql.substring(0, contentPos);
			String valueContent = sql.substring(contentPos, sql.length());
			
			List<String> valueContentList = new ArrayList<String>();
			ArrayList<Object> objList = new ArrayList<Object>();
			
			for(SymbolRecord defaultSpread : defaultSpreads){
				Object[] objs = new Object[]{
						defaultSpread.getSymbol(),
						defaultSpread.getSpread()
				};
				objList.addAll(Arrays.asList(objs));
				valueContentList.add(valueContent);
			}
			
			sql = insertStatement + StringUtils.join(valueContentList, ",");
			int res = this.jdbcTemplate.update(sql, objList.toArray());
		}
	}
	
}
