package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class IbBonusMapBean{

	private String brand_code;
	private String ib_code;
	private String bonus_code;
	private Date start_date;
	private Date end_date;
	private Date last_update_time;
	private String last_update_user;

	public void setBrand_code(String brand_code){
		this.brand_code = brand_code;
	}
	public String getBrand_code(){
		return brand_code;
	}
	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setBonus_code(String bonus_code){
		this.bonus_code = bonus_code;
	}
	public String getBonus_code(){
		return bonus_code;
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
		if(brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if(bonus_code != null)
			builder.append("bonus_code: " + bonus_code + ", ");
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
