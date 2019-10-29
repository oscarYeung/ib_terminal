package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class SettingsUsdCurrencyExchangeBean{

	private String base_currency;
	private String exchange_symbol;
	private Boolean is_cross;
	private Date last_update_time;

	public void setBase_currency(String base_currency){
		this.base_currency = base_currency;
	}
	public String getBase_currency(){
		return base_currency;
	}
	public void setExchange_symbol(String exchange_symbol){
		this.exchange_symbol = exchange_symbol;
	}
	public String getExchange_symbol(){
		return exchange_symbol;
	}
	public void setIs_cross(Boolean is_cross){
		this.is_cross = is_cross;
	}
	public Boolean getIs_cross(){
		return is_cross;
	}
	public void setLast_update_time(Date last_update_time){
		this.last_update_time = last_update_time;
	}
	public Date getLast_update_time(){
		return last_update_time;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(base_currency != null)
			builder.append("base_currency: " + base_currency + ", ");
		if(exchange_symbol != null)
			builder.append("exchange_symbol: " + exchange_symbol + ", ");
		builder.append("is_cross: " + is_cross + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		return builder.toString();
	}
}
