package com.henyep.ib.terminal.server.dto.mt4.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;

public class SymbolRecord {
	@SerializedName("Description")
	private String description;
	@SerializedName("Digits")
	private Integer digits;
	@SerializedName("Contract_size")
	private Integer contract_size;
	@SerializedName("Currency")
	private String currency;
	@SerializedName("Symbol")
	private String symbol;
	@SerializedName("Spread")
	private Integer spread;
	@SerializedName("Tick_size")
	private Double tickSize;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getDigits() {
		return digits;
	}
	public void setDigits(Integer digits) {
		this.digits = digits;
	}
	public Integer getContract_size() {
		return contract_size;
	}
	public void setContract_size(Integer contract_size) {
		this.contract_size = contract_size;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Integer getSpread() {
		return spread;
	}
	public void setSpread(Integer spread) {
		this.spread = spread;
	}
	public Double getTickSize() {
		return tickSize;
	}
	public void setTickSize(Double tickSize) {
		this.tickSize = tickSize;
	}

	public SettingsSymbolBean getSettingsSymbolBean(Date tradeDate){
		SettingsSymbolBean bean = new SettingsSymbolBean();
		bean.setBase_currency_rate(1.0);
		bean.setClosing_rate(0.0);
		bean.setContract_size(this.contract_size);
		bean.setCurrency("USD");
		bean.setSpread(this.spread);
		bean.setSymbol(this.symbol);
		bean.setTick_size(this.getTickSize());
		bean.setTrade_date(tradeDate);
		return bean;
	}
	

	
}
