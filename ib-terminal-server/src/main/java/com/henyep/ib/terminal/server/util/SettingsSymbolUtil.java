package com.henyep.ib.terminal.server.util;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;

public class SettingsSymbolUtil {

	private List<SettingsSymbolBean> beanList;
	
	public SettingsSymbolUtil(List<SettingsSymbolBean> beanList){
		
		this.beanList = beanList;
	}
	
	public SettingsSymbolBean GetSettingBean(String symbol){
		
		for(SettingsSymbolBean bean : beanList){
			if(bean.getSymbol().equals(symbol)){
				return bean;
			}
		}
		return null;
		
	}
	
	
	
}
