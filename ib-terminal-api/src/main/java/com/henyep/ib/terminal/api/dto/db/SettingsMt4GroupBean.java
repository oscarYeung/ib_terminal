package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class SettingsMt4GroupBean{

	private String mt4_group;
	private String product_group;
	private String currency;
	private Double fix_commission;
	private Double spread_commission;
	private Date start_date;
	private Date end_date;
	private Date last_update_time;
	private String last_update_user;

	public void setMt4_group(String mt4_group){
		this.mt4_group = mt4_group;
	}
	public String getMt4_group(){
		return mt4_group;
	}
	public void setProduct_group(String product_group){
		this.product_group = product_group;
	}
	public String getProduct_group(){
		return product_group;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getCurrency(){
		return currency;
	}
	public void setFix_commission(Double fix_commission){
		this.fix_commission = fix_commission;
	}
	public Double getFix_commission(){
		return fix_commission;
	}
	public void setSpread_commission(Double spread_commission){
		this.spread_commission = spread_commission;
	}
	public Double getSpread_commission(){
		return spread_commission;
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
		if(mt4_group != null)
			builder.append("mt4_group: " + mt4_group + ", ");
		if(product_group != null)
			builder.append("product_group: " + product_group + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("fix_commission: " + fix_commission + ", ");
		builder.append("spread_commission: " + spread_commission + ", ");
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
