package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class SettingsSymbolBean{

	private String symbol;
	private String currency;
	private Double tick_size;
	private Integer contract_size;
	private Integer spread;
	private Double closing_rate;
	private Double base_currency_rate;
	private Date trade_date;
	private Date last_update_time;
	private String last_update_user;

	public void setSymbol(String symbol){
		this.symbol = symbol;
	}
	public String getSymbol(){
		return symbol;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getCurrency(){
		return currency;
	}
	public void setTick_size(Double tick_size){
		this.tick_size = tick_size;
	}
	public Double getTick_size(){
		return tick_size;
	}
	public void setContract_size(Integer contract_size){
		this.contract_size = contract_size;
	}
	public Integer getContract_size(){
		return contract_size;
	}
	public void setSpread(Integer spread){
		this.spread = spread;
	}
	public Integer getSpread(){
		return spread;
	}
	public Double getClosing_rate() {
		return closing_rate;
	}
	public void setClosing_rate(Double closing_rate) {
		this.closing_rate = closing_rate;
	}
	public Double getBase_currency_rate() {
		return base_currency_rate;
	}
	public void setBase_currency_rate(Double base_currency_rate) {
		this.base_currency_rate = base_currency_rate;
	}
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public void setLast_update_time(Date last_update_time){
		this.last_update_time = last_update_time;
	}
	public Date getLast_update_time(){
		return last_update_time;
	}
	public void setLast_update_user(String last_update_user){
		this.last_update_user = last_update_user;
	}
	public String getLast_update_user(){
		return last_update_user;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(symbol != null)
			builder.append("symbol: " + symbol + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("tick_size: " + tick_size + ", ");
		builder.append("contract_size: " + contract_size + ", ");
		builder.append("spread: " + spread + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
