package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class ClientDetailsBean {

	private String client_trading_id;
	private String mt4_group;
	private String first_name;
	private String last_name;
	private String chinese_name;
	private String email;
	private String trading_platform;
	private String sex;
	private String mobile;
	private String currency;
	
	private Double account_balance;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)
	private Date register_date;
	@JsonFormat(pattern = Constants.FORMAT_DATETIME)	
	private Date last_update_time;
	private String last_update_user;

	public void setClient_trading_id(String client_trading_id) {
		this.client_trading_id = client_trading_id;
	}

	public String getClient_trading_id() {
		return client_trading_id;
	}

	public String getMt4_group() {
		return mt4_group;
	}

	public void setMt4_group(String mt4_group) {
		this.mt4_group = mt4_group;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setChinese_name(String chinese_name) {
		this.chinese_name = chinese_name;
	}

	public String getChinese_name() {
		return chinese_name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setTrading_platform(String trading_platform) {
		this.trading_platform = trading_platform;
	}

	public String getTrading_platform() {
		return trading_platform;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}

	public Double getAccount_balance() {
		return account_balance;
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
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}


	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (client_trading_id != null)
			builder.append("client_trading_id: " + client_trading_id + ", ");
		if (first_name != null)
			builder.append("first_name: " + first_name + ", ");
		if (last_name != null)
			builder.append("last_name: " + last_name + ", ");
		if (chinese_name != null)
			builder.append("chinese_name: " + chinese_name + ", ");
		if (email != null)
			builder.append("email: " + email + ", ");
		if (trading_platform != null)
			builder.append("trading_platform: " + trading_platform + ", ");
		if (sex != null)
			builder.append("sex: " + sex + ", ");
		if (mobile != null)
			builder.append("mobile: " + mobile + ", ");
		if (currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("account_balance: " + account_balance + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
