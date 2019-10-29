package com.henyep.ib.terminal.server.dto.report;

import java.util.Date;

public class IbRebateReportDto {

	private String ib_code;
	private String ib_name;
	private String brand_code;
	private Date start_date;
	private Date end_date;
	private Double accumulated_rebate;
	private Double accumulated_trade_pl;
	private Double rebate_pl_ratio;
	private Double margin_out_total;
	private Integer margin_out_count;
	private Double account_balance;
	
	private Date trade_date;
	private Date last_trade_date;
	private Double trade_date_rebate;
	private Double last_trade_date_rebate;
	
	private Double rebate_diff;
	
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public String getIb_name() {
		return ib_name;
	}
	public void setIb_name(String ib_name) {
		this.ib_name = ib_name;
	}
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
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
	public Double getAccumulated_rebate() {
		return accumulated_rebate;
	}
	public void setAccumulated_rebate(Double accumulated_rebate) {
		this.accumulated_rebate = accumulated_rebate;
	}
	public Double getAccumulated_trade_pl() {
		return accumulated_trade_pl;
	}
	public void setAccumulated_trade_pl(Double accumulated_trade_pl) {
		this.accumulated_trade_pl = accumulated_trade_pl;
	}
	public Double getRebate_pl_ratio() {
		return rebate_pl_ratio;
	}
	public void setRebate_pl_ratio(Double rebate_pl_ratio) {
		this.rebate_pl_ratio = rebate_pl_ratio;
	}
	public Double getMargin_out_total() {
		return margin_out_total;
	}
	public void setMargin_out_total(Double margin_out_total) {
		this.margin_out_total = margin_out_total;
	}
	public Integer getMargin_out_count() {
		return margin_out_count;
	}
	public void setMargin_out_count(Integer margin_out_count) {
		this.margin_out_count = margin_out_count;
	}
	public Double getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public Date getLast_trade_date() {
		return last_trade_date;
	}
	public void setLast_trade_date(Date last_trade_date) {
		this.last_trade_date = last_trade_date;
	}
	public Double getTrade_date_rebate() {
		return trade_date_rebate;
	}
	public void setTrade_date_rebate(Double trade_date_rebate) {
		this.trade_date_rebate = trade_date_rebate;
	}
	public Double getLast_trade_date_rebate() {
		return last_trade_date_rebate;
	}
	public void setLast_trade_date_rebate(Double last_trade_date_rebate) {
		this.last_trade_date_rebate = last_trade_date_rebate;
	}
	public Double getRebate_diff() {
		return rebate_diff;
	}
	public void setRebate_diff(Double rebate_diff) {
		this.rebate_diff = rebate_diff;
	}
	
	
}
