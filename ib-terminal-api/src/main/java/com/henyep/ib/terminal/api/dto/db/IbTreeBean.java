package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbTreeBean {

	private Integer id;
	private String brand_code;
	private String team;
	private String ib_code;
	private Integer min_id;
	private Integer max_id;
	private boolean is_ib;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date start_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date end_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
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
	public void setTeam(String team){
		this.team = team;
	}
	public String getTeam(){
		return team;
	}
	public void setIb_code(String ib_code){
		this.ib_code = ib_code;
	}
	public String getIb_code(){
		return ib_code;
	}
	public void setMin_id(Integer min_id){
		this.min_id = min_id;
	}
	public Integer getMin_id(){
		return min_id;
	}
	public void setMax_id(Integer max_id){
		this.max_id = max_id;
	}
	public Integer getMax_id(){
		return max_id;
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
	public boolean isIs_ib() {
		return is_ib;
	}
	public void setIs_ib(boolean is_ib) {
		this.is_ib = is_ib;
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("id: " + id + ", ");
		if(brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if(team != null)
			builder.append("team: " + team + ", ");
		if(ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		builder.append("min_id: " + min_id + ", ");
		builder.append("max_id: " + max_id + ", ");
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
