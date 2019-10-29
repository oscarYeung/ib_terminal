package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class UserBean {

	@NotBlank(message = "{user.brand.code.blank}")
	@NotNull(message = "{user.brand.code.blank}")
	private String brand_code;
	@NotBlank(message = "{user.code.blank}")
	@NotNull(message = "{user.code.blank}")
	private String user_code;
	@NotBlank(message = "{user.name.blank}")
	@NotNull(message = "{user.name.blank}")
	
	private String user_name;
	@NotBlank(message = "{user.password.blank}")
	@NotNull(message = "{user.password.blank}")
	private String password;
	@NotBlank(message = "{user.status.blank}")
	@NotNull(message = "{user.status.blank}")
	private String status;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date create_time;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}

	public String getUser_code() {
		return user_code;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getCreate_time() {
		return create_time;
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
		if (brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if (user_code != null)
			builder.append("user_code: " + user_code + ", ");
		if (user_name != null)
			builder.append("user_name: " + user_name + ", ");
		if (password != null)
			builder.append("password: " + password + ", ");
		if (status != null)
			builder.append("status: " + status + ", ");
		if (create_time != null)
			builder.append("create_time: " + create_time.toString() + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}

}
