package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class RebateCpaBean{

	private String rebate_code;
	private String currency;
	private Double amount;
	private Double min_lot;
	private Double min_deposit;
	private Date start_date;
	private Date end_date;
	private Date last_update_time;
	private String last_update_user;

	public void setRebate_code(String rebate_code){
		this.rebate_code = rebate_code;
	}
	public String getRebate_code(){
		return rebate_code;
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
	public void setMin_lot(Double min_lot){
		this.min_lot = min_lot;
	}
	public Double getMin_lot(){
		return min_lot;
	}
	public void setMin_deposit(Double min_deposit){
		this.min_deposit = min_deposit;
	}
	public Double getMin_deposit(){
		return min_deposit;
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
		if(rebate_code != null)
			builder.append("rebate_code: " + rebate_code + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("amount: " + amount + ", ");
		builder.append("min_lot: " + min_lot + ", ");
		builder.append("min_deposit: " + min_deposit + ", ");
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
