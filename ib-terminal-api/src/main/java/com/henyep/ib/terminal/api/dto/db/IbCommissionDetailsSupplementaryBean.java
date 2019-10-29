package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbCommissionDetailsSupplementaryBean {

	private String brand_code;
	private String ib_code;
	private String group_code;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date trade_date;
	private String currency;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Double ev_commission;
	private Double total_equity_change;
	private Double client_fix_commission;
	private Double client_rebate_commission;
	private Double margin_in_bonus;
	private Double deposit_bonus;
	private Double credit_card_charges;
	private Double deficit;
	private Double net_ev;
	private String rebate_code;
	private Double ev_percentage;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.FORMAT_DATETIME)
	private Date last_update_time;
	private String last_update_user;
	private Double adjustment;
	private Double netMargin;

	public Double getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}

	public Double getNetMargin() {
		return netMargin;
	}

	public void setNetMargin(Double netMargin) {
		this.netMargin = netMargin;
	}

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}

	public String getIb_code() {
		return ib_code;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setEv_commission(Double ev_commission) {
		this.ev_commission = ev_commission;
	}

	public Double getEv_commission() {
		return ev_commission;
	}

	public void setTotal_equity_change(Double total_equity_change) {
		this.total_equity_change = total_equity_change;
	}

	public Double getTotal_equity_change() {
		return total_equity_change;
	}

	public void setClient_fix_commission(Double client_fix_commission) {
		this.client_fix_commission = client_fix_commission;
	}

	public Double getClient_fix_commission() {
		return client_fix_commission;
	}

	public void setClient_rebate_commission(Double client_rebate_commission) {
		this.client_rebate_commission = client_rebate_commission;
	}

	public Double getClient_rebate_commission() {
		return client_rebate_commission;
	}

	public void setMargin_in_bonus(Double margin_in_bonus) {
		this.margin_in_bonus = margin_in_bonus;
	}

	public Double getMargin_in_bonus() {
		return margin_in_bonus;
	}

	public void setDeposit_bonus(Double deposit_bonus) {
		this.deposit_bonus = deposit_bonus;
	}

	public Double getDeposit_bonus() {
		return deposit_bonus;
	}

	public Double getCredit_card_charges() {
		return credit_card_charges;
	}

	public void setCredit_card_charges(Double credit_card_charges) {
		this.credit_card_charges = credit_card_charges;
	}

	public void setDeficit(Double deficit) {
		this.deficit = deficit;
	}

	public Double getDeficit() {
		return deficit;
	}

	public void setNet_ev(Double net_ev) {
		this.net_ev = net_ev;
	}

	public Double getNet_ev() {
		return net_ev;
	}

	public void setRebate_code(String rebate_code) {
		this.rebate_code = rebate_code;
	}

	public String getRebate_code() {
		return rebate_code;
	}

	public void setEv_percentage(Double ev_percentage) {
		this.ev_percentage = ev_percentage;
	}

	public Double getEv_percentage() {
		return ev_percentage;
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
		if (ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if (group_code != null)
			builder.append("group_code: " + group_code + ", ");
		
		if (trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		
		if (currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("ev_commission: " + ev_commission + ", ");
		builder.append("total_equity_change: " + total_equity_change + ", ");
		builder.append("client_fix_commission: " + client_fix_commission + ", ");
		builder.append("client_rebate_commission: " + client_rebate_commission + ", ");
		builder.append("margin_in_bonus: " + margin_in_bonus + ", ");
		builder.append("deposit_bonus: " + deposit_bonus + ", ");
		builder.append("deficit: " + deficit + ", ");
		builder.append("net_ev: " + net_ev + ", ");
		if (rebate_code != null)
			builder.append("rebate_code: " + rebate_code + ", ");
		builder.append("ev_percentage: " + ev_percentage + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
