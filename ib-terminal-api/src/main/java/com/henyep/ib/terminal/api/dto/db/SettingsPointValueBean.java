package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class SettingsPointValueBean{

	private String symbol;
	private String currency;
	private Double amount;
	private Date start_date;
	private Date end_date;
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
	public void setAmount(Double amount){
		this.amount = amount;
	}
	public Double getAmount(){
		return amount;
	}
	public void setStart_date(Date start_date){
		this.start_date = start_date;
	}
	public Date getStart_date(){
		return start_date;
	}
	public void setEnd_date(Date end_date){
		this.end_date = end_date;
	}
	public Date getEnd_date(){
		return end_date;
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
		builder.append("amount: " + amount + ", ");
		if(start_date != null)
			builder.append("start_date: " + start_date.toString() + ", ");
		if(end_date != null)
			builder.append("end_date: " + end_date.toString() + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
