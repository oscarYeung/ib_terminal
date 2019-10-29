package com.henyep.ib.terminal.server.util;

import java.util.List;


import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;
import com.henyep.ib.terminal.api.dto.db.SettingsUsdCurrencyExchangeBean;

public class SettingsSymbolUtil {
	

	private List<SettingsSymbolBean> beanList;
	
	private List<SettingsUsdCurrencyExchangeBean> exchangeBeanList;
	
	public SettingsSymbolUtil(List<SettingsSymbolBean> beanList, List<SettingsUsdCurrencyExchangeBean> exchangeBeans){
		
		this.beanList = beanList;
		this.exchangeBeanList = exchangeBeans;
	}
	
	public SettingsSymbolBean GetSettingBean(String symbol){
		
		for(SettingsSymbolBean bean : beanList){
			if(bean.getSymbol().equals(symbol)){
				return bean;
			}
		}
		return null;
	}
	
	
	public Double GetExchangeRate(String baseCurrency){
		
		if(baseCurrency.equals("USD")){
			return 1.0;
		}
		
		for(SettingsUsdCurrencyExchangeBean exchangeBean : this.exchangeBeanList){
			
			if(exchangeBean.getBase_currency().equals(baseCurrency)){
				String exchangeSymbol = exchangeBean.getExchange_symbol();
				for(SettingsSymbolBean bean : beanList){
					if(bean.getSymbol().equals(exchangeSymbol)){
						if(exchangeBean.getIs_cross()){
							System.out.println(baseCurrency + " 1/xxxxxxx" + 1 / bean.getClosing_rate());
							return 1 / bean.getClosing_rate();
						}
						else{
							System.out.println(baseCurrency + " xxxxxxx" + bean.getClosing_rate().toString());
							return bean.getClosing_rate();
						}
					}
				}
			}
		}
		System.out.println("oh return null");
		return null;
		
	}
	
	
	
}
