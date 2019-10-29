package com.henyep.ib.terminal.server.dto.report;

import java.util.Date;

public class ClientSummaryPeriodDto {
	private String brand_code;
	private String platform;
	private String ib_code;
	private String client_code;
	private String client_ib_code;
	private String product_group;
	private Date trade_date;
	private String currency;
	private Double total_lot;
	private Double total_fix_commission;
	private Double total_spread_commission;
	private Double total_rebate_commission_lot;
	private Double total_rebate_commission_pip;
	private Double total_commission;
	private Double net_deposit;
	private Double net_adj;
	private Double total_trade_pl;
	private Double total_trade_swaps;
	private Date last_update_time;
	private String last_update_user;
	
	public String getBrand_code() {
		return brand_code;
	}
	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	public String getClient_ib_code() {
		return client_ib_code;
	}
	public void setClient_ib_code(String client_ib_code) {
		this.client_ib_code = client_ib_code;
	}
	public String getProduct_group() {
		return product_group;
	}
	public void setProduct_group(String product_group) {
		this.product_group = product_group;
	}
	public Date getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getTotal_lot() {
		return total_lot;
	}
	public void setTotal_lot(Double total_lot) {
		this.total_lot = total_lot;
	}
	public Double getTotal_fix_commission() {
		return total_fix_commission;
	}
	public void setTotal_fix_commission(Double total_fix_commission) {
		this.total_fix_commission = total_fix_commission;
	}
	public Double getTotal_spread_commission() {
		return total_spread_commission;
	}
	public void setTotal_spread_commission(Double total_spread_commission) {
		this.total_spread_commission = total_spread_commission;
	}
	public Double getTotal_rebate_commission_lot() {
		return total_rebate_commission_lot;
	}
	public void setTotal_rebate_commission_lot(Double total_rebate_commission_lot) {
		this.total_rebate_commission_lot = total_rebate_commission_lot;
	}
	public Double getTotal_rebate_commission_pip() {
		return total_rebate_commission_pip;
	}
	public void setTotal_rebate_commission_pip(Double total_rebate_commission_pip) {
		this.total_rebate_commission_pip = total_rebate_commission_pip;
	}
	public Double getTotal_commission() {
		return total_commission;
	}
	public void setTotal_commission(Double total_commission) {
		this.total_commission = total_commission;
	}
	public Double getNet_deposit() {
		return net_deposit;
	}
	public void setNet_deposit(Double net_deposit) {
		this.net_deposit = net_deposit;
	}
	public Double getNet_adj() {
		return net_adj;
	}
	public void setNet_adj(Double net_adj) {
		this.net_adj = net_adj;
	}
	public Double getTotal_trade_pl() {
		return total_trade_pl;
	}
	public void setTotal_trade_pl(Double total_trade_pl) {
		this.total_trade_pl = total_trade_pl;
	}
	public Double getTotal_trade_swaps() {
		return total_trade_swaps;
	}
	public void setTotal_trade_swaps(Double total_trade_swaps) {
		this.total_trade_swaps = total_trade_swaps;
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
