package com.henyep.ib.terminal.server.util;

import java.util.Date;
import java.util.List;

import com.henyep.ib.terminal.api.dto.db.SettingsPointValueBean;

public class SettingsPointValueUtil {
	private List<SettingsPointValueBean> settings;
	
	public SettingsPointValueUtil(List<SettingsPointValueBean> settings){
		this.settings = settings;
	}
	
	public SettingsPointValueBean getSettingsPoint(String symbolName, Date tradeDate) {

		for (SettingsPointValueBean setting : settings) {
			if (setting.getSymbol().equals(symbolName)) {
				if (setting.getEnd_date() != null) {
					if (setting.getStart_date().before(tradeDate) && setting.getEnd_date().after(tradeDate)) {
						return setting;
					}
				} else {
					if (setting.getStart_date().before(tradeDate)) {
						return setting;
					}
				}
			}
		}
		return null;
	}
}
