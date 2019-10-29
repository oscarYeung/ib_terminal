package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class RebateDetailsBean {

	private String rebate_code;
	@NotBlank(message = Constants.ERR_COMMON_PRODUCT_GROUP_IS_BLANK)
	private String product_group;
	@NotBlank(message = Constants.ERR_COMMON_CLIENT_PACKAGE_CODE_IS_BLANK)
	private String client_package_code;
	private String group_code;
	private String spread_type;
	private String currency;
	private Double min_lot;
	private String client_fix_commission_type;
	private Double client_fix_commission;
	private String client_spread_commission_type;
	private Double client_spread_commission;
	private String rebate_method;
	private String rebate_type;
	private Double rebate_commission;
	private String rebate_to_master_type;
	private Double rebate_to_master;
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

	public void setProduct_group(String product_group) {
		this.product_group = product_group;
	}

	public String getProduct_group() {
		return product_group;
	}

	public void setClient_package_code(String client_package_code) {
		this.client_package_code = client_package_code;
	}

	public String getClient_package_code() {
		return client_package_code;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getSpread_type() {
		return spread_type;
	}

	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setMin_lot(Double min_lot) {
		this.min_lot = min_lot;
	}

	public Double getMin_lot() {
		return min_lot;
	}

	public void setClient_fix_commission_type(String client_fix_commission_type) {
		this.client_fix_commission_type = client_fix_commission_type;
	}

	public String getClient_fix_commission_type() {
		return client_fix_commission_type;
	}

	public void setClient_fix_commission(Double client_fix_commission) {
		this.client_fix_commission = client_fix_commission;
	}

	public Double getClient_fix_commission() {
		return client_fix_commission;
	}

	public void setClient_spread_commission_type(String client_spread_commission_type) {
		this.client_spread_commission_type = client_spread_commission_type;
	}

	public String getClient_spread_commission_type() {
		return client_spread_commission_type;
	}

	public void setClient_spread_commission(Double client_spread_commission) {
		this.client_spread_commission = client_spread_commission;
	}

	public Double getClient_spread_commission() {
		return client_spread_commission;
	}

	public void setRebate_type(String rebate_type) {
		this.rebate_type = rebate_type;
	}

	public String getRebate_type() {
		return rebate_type;
	}

	public void setRebate_commission(Double rebate_commission) {
		this.rebate_commission = rebate_commission;
	}

	public Double getRebate_commission() {
		return rebate_commission;
	}
	

	public String getRebate_method() {
		return rebate_method;
	}

	public void setRebate_method(String rebate_method) {
		this.rebate_method = rebate_method;
	}

	public String getRebate_to_master_type() {
		return rebate_to_master_type;
	}

	public void setRebate_to_master_type(String rebate_to_master_type) {
		this.rebate_to_master_type = rebate_to_master_type;
	}

	public Double getRebate_to_master() {
		return rebate_to_master;
	}

	public void setRebate_to_master(Double rebate_to_master) {
		this.rebate_to_master = rebate_to_master;
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
		if (product_group != null)
			builder.append("product_group: " + product_group + ", ");
		if (client_package_code != null)
			builder.append("client_package_code: " + client_package_code + ", ");
		if (currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("min_lot: " + min_lot + ", ");
		if (client_fix_commission_type != null)
			builder.append("client_fix_commission_type: " + client_fix_commission_type + ", ");
		builder.append("client_fix_commission: " + client_fix_commission + ", ");
		if (client_spread_commission_type != null)
			builder.append("client_spread_commission_type: " + client_spread_commission_type + ", ");
		builder.append("client_spread_commission: " + client_spread_commission + ", ");
		if (rebate_type != null)
			builder.append("rebate_type: " + rebate_type + ", ");
		builder.append("rebate_commission: " + rebate_commission + ", ");
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
