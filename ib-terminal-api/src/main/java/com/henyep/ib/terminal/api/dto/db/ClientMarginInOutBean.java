package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class ClientMarginInOutBean{

	private String order_id;
	private String client_code;
	private String platform;
	private String currency;
	private Double amount;
	private Double amountUsd;
	private String comment;
	private Integer trade_type_id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setOrder_id(String order_id){
		this.order_id = order_id;
	}
	public String getOrder_id(){
		return order_id;
	}
	public void setClient_code(String client_code){
		this.client_code = client_code;
	}
	public String getClient_code(){
		return client_code;
	}
	public void setPlatform(String platform){
		this.platform = platform;
	}
	public String getPlatform(){
		return platform;
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
	public Double getAmountUsd() {
		return amountUsd;
	}
	public void setAmountUsd(Double amountUsd) {
		this.amountUsd = amountUsd;
	}
	public void setComment(String comment){
		this.comment = comment;
	}
	public String getComment(){
		return comment;
	}
	public Integer getTrade_type_id() {
		return trade_type_id;
	}
	public void setTrade_type_id(Integer trade_type_id) {
		this.trade_type_id = trade_type_id;
	}
	public void setTrade_date(Date trade_date){
		this.trade_date = trade_date;
	}
	public Date getTrade_date(){
		return trade_date;
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
		if(order_id != null)
			builder.append("order_id: " + order_id + ", ");
		if(client_code != null)
			builder.append("client_code: " + client_code + ", ");
		if(platform != null)
			builder.append("platform: " + platform + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("amount: " + amount + ", ");
		if(comment != null)
			builder.append("comment: " + comment + ", ");
		if(trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
