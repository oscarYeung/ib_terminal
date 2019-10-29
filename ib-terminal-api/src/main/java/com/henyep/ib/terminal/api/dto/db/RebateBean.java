package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class RebateBean {

	private Integer id;
	private String rebate_code;
	private String brand_code;
	private Boolean ignore_tree_rebate;
	private String description;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIgnore_tree_rebate() {
		return ignore_tree_rebate;
	}

	public void setIgnore_tree_rebate(Boolean ignore_tree_rebate) {
		this.ignore_tree_rebate = ignore_tree_rebate;
	}

	public void setRebate_code(String rebate_code) {
		this.rebate_code = rebate_code;
	}

	public String getRebate_code() {
		return rebate_code;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}

	public Date getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_user(String last_update_user) {
		this.last_update_user = last_update_user;
	}

	public String getLast_update_user() {
		return last_update_user;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id: " + id + ", ");
		if (rebate_code != null)
			builder.append("rebate_code: " + rebate_code + ", ");
		if (brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if (description != null)
			builder.append("description: " + description + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}

}
