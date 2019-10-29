package com.henyep.ib.terminal.server.dto.sales.ib.tree;

import java.util.Date;

public class UserIbTreeDto {
	private Integer id;
	private String brand_code;
	private String user_code;
	private String ib_team_head_id;
	private Integer min_id;
	private Integer max_id;
	private Date start_date;
	private Date end_date;
	private Date last_update_time;
	private String last_update_user;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getIb_team_head_id() {
		return ib_team_head_id;
	}
	public void setIb_team_head_id(String ib_team_head_id) {
		this.ib_team_head_id = ib_team_head_id;
	}
	public Integer getMin_id() {
		return min_id;
	}
	public void setMin_id(Integer min_id) {
		this.min_id = min_id;
	}
	public Integer getMax_id() {
		return max_id;
	}
	public void setMax_id(Integer max_id) {
		this.max_id = max_id;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public Date getLast_update_time() {
		return last_update_time;
	}
	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}
	public String getLast_update_user() {
		return last_update_user;
	}
	public void setLast_update_user(String last_update_user) {
		this.last_update_user = last_update_user;
	}
	
	
	
	
	
}
