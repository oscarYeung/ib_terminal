package com.henyep.ib.terminal.server.dto.mt4.model;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.henyep.ib.terminal.api.dto.db.SettingsSymbolBean;

public class MT5SymbolRecord {

	@SerializedName("Description")
	private String description;
	@SerializedName("Digits")
	private Integer digits;
	@SerializedName("ContractSize")
	private Integer contractSize;
	@SerializedName("BaseCurrency")
	private String baseCurrency;
	@SerializedName("Symbol")
	private String symbol;
	@SerializedName("Spread")
	private Integer spread;
	@SerializedName("Path")
	private String path;
	@SerializedName("TickSize")
	private BigDecimal tickSize;

	public MT5SymbolRecord() {
		// TODO Auto-generated constructor stub
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

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

	public Integer getContractSize() {
		return contractSize;
	}

	public void setContractSize(Integer contractSize) {
		this.contractSize = contractSize;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
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

	public BigDecimal getTickSize() {
		return tickSize;
	}

	public void setTickSize(BigDecimal tickSize) {
		this.tickSize = tickSize;
	}

	
	
	public SettingsSymbolBean getSettingsSymbolBean(Date tradeDate){
		SettingsSymbolBean bean = new SettingsSymbolBean();
		bean.setBase_currency_rate(1.0);
		bean.setClosing_rate(0.0);
		bean.setContract_size(this.contractSize);
		bean.setCurrency(this.baseCurrency);
		bean.setSpread(this.spread);
		bean.setSymbol(this.symbol);
		bean.setTick_size(this.tickSize.doubleValue());
		bean.setTrade_date(tradeDate);
		return bean;
	}

	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder();
		str.append(" Symbol: ").append(this.symbol);
		str.append(" Spread: ").append(this.spread);
		str.append(" TickSize: ").append(this.tickSize);
		str.append(" ContractSize: ").append(this.contractSize);
		str.append(" Digits: ").append(this.digits);
		
		return str.toString();
	}
	
	
}
