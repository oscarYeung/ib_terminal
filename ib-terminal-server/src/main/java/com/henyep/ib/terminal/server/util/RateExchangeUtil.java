package com.henyep.ib.terminal.server.util;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.server.dao.SettingsSymbolDao;

@Repository(value = "RateExchangeUtil")
public class RateExchangeUtil {

	@Resource(name = "SettingsSymbolDao")
	private SettingsSymbolDao settingsSymbolDao;
	
	
	private HashMap<String, Double> rateMapping = null;
	
	public void initMapping() throws Exception{

		rateMapping = new HashMap<String, Double>();
		
		List<SettingsSymbolBean> beans = settingsSymbolDao.getAllSettingsSymbols(new Date());
		for(SettingsSymbolBean bean : beans){
			if(!rateMapping.containsKey(bean.getSymbol())){
				rateMapping.put(bean.getSymbol(), bean.getClosing_rate());	
			}
		}
	}
	
	
	public Double[] getExchangePrice(String fromCurrency, String toCurrency, double fromAmount) throws Exception{
		
		//takeLog(fromCurrency + " -> "  + toCurrency);
		Double[] rtn = new Double[2];
		
		if(fromCurrency.equals(toCurrency)){
			rtn[0] = new Double(1);
			rtn[1] = fromAmount;
		}
		else{
			initMapping();
			String directSymbol = fromCurrency + toCurrency;
			String indirectSymbol = toCurrency + fromCurrency;
			
			if(rateMapping.containsKey(directSymbol)){
				rtn[0] = rateMapping.get(directSymbol);
				rtn[1] = fromAmount * rateMapping.get(directSymbol); 
			}
			else if(rateMapping.containsKey(indirectSymbol)){
				rtn[0] = 1 / rateMapping.get(indirectSymbol);
				rtn[1] = fromAmount / rateMapping.get(indirectSymbol);
			}
			else{
				Logger.getLogger(RateExchangeUtil.class).error("Fail to perform rate exchange, from " + fromCurrency + " to " + toCurrency);
				rtn[0] = null;
				rtn[1] = null;
			}
		}
		
		return rtn;
	}
	
	private void takeLog(String message){
		Logger.getLogger(RateExchangeUtil.class).debug(message);
		System.out.println(message);
	}
}
