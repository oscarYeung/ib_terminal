package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

public class IbAccountDetailsHistoryBean {

	private String ib_code;
	private String currency;
	private Date trade_date;
	private Double account_balance;
	private Double pending_commission;
	private Double day_open;
	private Double day_open_pending_commission;
	private Double month_to_date;
	private Double year_to_date;
	private Double net_margin_bonus;
	private Date last_update_time;
	private String last_update_user;

	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getCurrency(){
		return currency;
	}
	
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public void setAccount_balance(Double account_balance){
		this.account_balance = account_balance;
	}
	public Double getAccount_balance(){
		return account_balance;
	}
	public void setPending_commission(Double pending_commission){
		this.pending_commission = pending_commission;
	}
	public Double getPending_commission(){
		return pending_commission;
	}
	public void setDay_open(Double day_open){
		this.day_open = day_open;
	}
	public Double getDay_open(){
		return day_open;
	}
	public void setDay_open_pending_commission(Double day_open_pending_commission){
		this.day_open_pending_commission = day_open_pending_commission;
	}
	public Double getDay_open_pending_commission(){
		return day_open_pending_commission;
	}
	public void setMonth_to_date(Double month_to_date){
		this.month_to_date = month_to_date;
	}
	public Double getMonth_to_date(){
		return month_to_date;
	}
	public void setYear_to_date(Double year_to_date){
		this.year_to_date = year_to_date;
	}
	public Double getYear_to_date(){
		return year_to_date;
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
	public Double getNet_margin_bonus() {
		return net_margin_bonus;
	}
	public void setNet_margin_bonus(Double net_margin_bonus) {
		this.net_margin_bonus = net_margin_bonus;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("account_balance: " + account_balance + ", ");
		builder.append("pending_commission: " + pending_commission + ", ");
		builder.append("day_open: " + day_open + ", ");
		builder.append("day_open_pending_commission: " + day_open_pending_commission + ", ");
		builder.append("month_to_date: " + month_to_date + ", ");
		builder.append("year_to_date: " + year_to_date + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}


