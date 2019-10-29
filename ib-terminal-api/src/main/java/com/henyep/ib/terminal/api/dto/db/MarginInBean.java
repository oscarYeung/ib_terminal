package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class MarginInBean{

	private Integer id;
	private String brand_code;
	private String category;
	private String account;
	private String currency;
	private Double amount;
	private String account_currency;
	private Double exchange_rate;
	private Double account_amount;
	private Integer transfer_id;
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	private String status;
	private String comment;
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date create_time;
	private String create_user;
	@JsonFormat(pattern=Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;
	

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}
	public void setBrand_code(String brand_code){
		this.brand_code = brand_code;
	}
	public String getBrand_code(){
		return brand_code;
	}
	public void setCategory(String category){
		this.category = category;
	}
	public String getCategory(){
		return category;
	}
	public void setAccount(String account){
		this.account = account;
	}
	public String getAccount(){
		return account;
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
	public void setAccount_currency(String account_currency){
		this.account_currency = account_currency;
	}
	public String getAccount_currency(){
		return account_currency;
	}
	public void setExchange_rate(Double exchange_rate){
		this.exchange_rate = exchange_rate;
	}
	public Double getExchange_rate(){
		return exchange_rate;
	}
	public void setAccount_amount(Double account_amount){
		this.account_amount = account_amount;
	}
	public Double getAccount_amount(){
		return account_amount;
	}
	public void setTransfer_id(Integer transfer_id){
		this.transfer_id = transfer_id;
	}
	public Integer getTransfer_id(){
		return transfer_id;
	}
	public void setTrade_date(Date trade_date){
		this.trade_date = trade_date;
	}
	public Date getTrade_date(){
		return trade_date;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getStatus(){
		return status;
	}
	public void setComment(String comment){
		this.comment = comment;
	}
	public String getComment(){
		return comment;
	}
	public void setCreate_time(Date create_time){
		this.create_time = create_time;
	}
	public Date getCreate_time(){
		return create_time;
	}
	public void setCreate_user(String create_user){
		this.create_user = create_user;
	}
	public String getCreate_user(){
		return create_user;
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
		builder.append("id: " + id + ", ");
		if(brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if(category != null)
			builder.append("category: " + category + ", ");
		if(account != null)
			builder.append("account: " + account + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("amount: " + amount + ", ");
		if(account_currency != null)
			builder.append("account_currency: " + account_currency + ", ");
		builder.append("exchange_rate: " + exchange_rate + ", ");
		builder.append("account_amount: " + account_amount + ", ");
		builder.append("transfer_id: " + transfer_id + ", ");
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if(status != null)
			builder.append("status: " + status + ", ");
		if(comment != null)
			builder.append("comment: " + comment + ", ");
		if(create_time != null)
			builder.append("create_time: " + create_time.toString() + ", ");
		if(create_user != null)
			builder.append("create_user: " + create_user + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
