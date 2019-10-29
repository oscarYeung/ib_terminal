package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbLeadBean {

	private String ib_code;
	private String salesforce_id;
	private String client_first_name;
	private String client_last_name;
	private String phone;
	private String email;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date registration_date;
	private String register_type;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setSalesforce_id(String salesforce_id) {
		this.salesforce_id = salesforce_id;
	}

	public String getSalesforce_id() {
		return salesforce_id;
	}

	public void setClient_first_name(String client_first_name) {
		this.client_first_name = client_first_name;
	}

	public String getClient_first_name() {
		return client_first_name;
	}

	public void setClient_last_name(String client_last_name) {
		this.client_last_name = client_last_name;
	}

	public String getClient_last_name() {
		return client_last_name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setRegistration_date(Date registration_date) {
		this.registration_date = registration_date;
	}

	public Date getRegistration_date() {
		return registration_date;
	}

	public void setRegister_type(String register_type) {
		this.register_type = register_type;
	}

	public String getRegister_type() {
		return register_type;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
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
		if (ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if (salesforce_id != null)
			builder.append("salesforce_id: " + salesforce_id + ", ");
		if (client_first_name != null)
			builder.append("client_first_name: " + client_first_name + ", ");
		if (client_last_name != null)
			builder.append("client_last_name: " + client_last_name + ", ");
		if (phone != null)
			builder.append("phone: " + phone + ", ");
		if (email != null)
			builder.append("email: " + email + ", ");
		if (registration_date != null)
			builder.append("registration_date: " + registration_date.toString() + ", ");
		if (register_type != null)
			builder.append("register_type: " + register_type + ", ");
		if (status != null)
			builder.append("status: " + status + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
