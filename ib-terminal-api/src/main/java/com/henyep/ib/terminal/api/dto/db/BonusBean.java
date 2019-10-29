package com.henyep.ib.terminal.api.dto.db;
import java.util.Date;

public class BonusBean{

	private Integer id;
	private String brand_code;
	private String bonus_code;
	private String description;
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
	public void setBonus_code(String bonus_code){
		this.bonus_code = bonus_code;
	}
	public String getBonus_code(){
		return bonus_code;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
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
		if(bonus_code != null)
			builder.append("bonus_code: " + bonus_code + ", ");
		if(description != null)
			builder.append("description: " + description + ", ");
		if(last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if(last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
