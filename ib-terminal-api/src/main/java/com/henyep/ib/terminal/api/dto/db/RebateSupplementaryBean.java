package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class RebateSupplementaryBean {

	private String rebate_code;

	private String group_code;

	private String currency;

	@NotNull(message = Constants.ERR_COMMON_EV_PRECENTAGE_IS_BLANK)
	private Double ev_percentage;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date start_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date end_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;

	public void setRebate_code(String rebate_code) {
		this.rebate_code = rebate_code;
	}

	public String getRebate_code() {
		return rebate_code;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setEv_percentage(Double ev_percentage) {
		this.ev_percentage = ev_percentage;
	}

	public Double getEv_percentage() {
		return ev_percentage;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getEnd_date() {
		return end_date;
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
		if (rebate_code != null)
			builder.append("rebate_code: " + rebate_code + ", ");
		builder.append("ev_percentage: " + ev_percentage + ", ");
		if (start_date != null)
			builder.append("start_date: " + start_date.toString() + ", ");
		if (end_date != null)
			builder.append("end_date: " + end_date.toString() + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}

}
