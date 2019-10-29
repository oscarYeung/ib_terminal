package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class BonusDetailsBean{

	private String bonus_code;
	private String currency;
	private String bonus_type;
	private Double min_amount;
	private Double award_percentage;
	private Date last_update_time;
	private String last_update_user;

	public void setBonus_code(String bonus_code){
		this.bonus_code = bonus_code;
	}
	public String getBonus_code(){
		return bonus_code;
	}
	public void setCurrency(String currency){
		this.currency = currency;
	}
	public String getCurrency(){
		return currency;
	}
	public void setBonus_type(String bonus_type){
		this.bonus_type = bonus_type;
	}
	public String getBonus_type(){
		return bonus_type;
	}
	public void setMin_amount(Double min_amount){
		this.min_amount = min_amount;
	}
	public Double getMin_amount(){
		return min_amount;
	}
	public void setAward_percentage(Double award_percentage){
		this.award_percentage = award_percentage;
	}
	public Double getAward_percentage(){
		return award_percentage;
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
		if(bonus_code != null)
			builder.append("bonus_code: " + bonus_code + ", ");
		if(currency != null)
			builder.append("currency: " + currency + ", ");
		if(bonus_type != null)
			builder.append("bonus_type: " + bonus_type + ", ");
		builder.append("min_amount: " + min_amount + ", ");
		builder.append("award_percentage: " + award_percentage + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
