package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

public class IbCommissionDetailsBean {

	private String brand_code;
	private String platform;
	private String ticket;
	private String ib_code;
	private String group_code;
	private String client_code;
	private String client_ib_code;
	private String product_group;
	private String spread_type;
	private Date trade_date;
	private String symbol;
	private String buy_sell;
	private double lot;
	private String currency;
	private double deposit;
	private double client_fix_commission;
	private double client_spread_commission;
	private double rebate_commission_lot;
	private double rebate_commission_pip;
	private double trade_swaps;
	private double trade_pl;
	private Date open_trade_time;
	private Date close_trade_time;
	private String rebate_code;
	private String rebate_type_lot;
	private double rebate_per_lot;
	private String rebate_type_pip;
	private double rebate_per_pip;
	private String jurisdiction;
	private Date last_update_time;
	private String last_update_user;

	public void setBrand_code(String brand_code) {
		this.brand_code = brand_code;
	}

	public String getBrand_code() {
		return brand_code;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
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

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_ib_code(String client_ib_code) {
		this.client_ib_code = client_ib_code;
	}

	public String getClient_ib_code() {
		return client_ib_code;
	}

	public void setProduct_group(String product_group) {
		this.product_group = product_group;
	}

	public String getProduct_group() {
		return product_group;
	}

	public String getSpread_type() {
		return spread_type;
	}

	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setBuy_sell(String buy_sell) {
		this.buy_sell = buy_sell;
	}

	public String getBuy_sell() {
		return buy_sell;
	}

	public void setLot(double lot) {
		this.lot = lot;
	}

	public double getLot() {
		return lot;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setClient_fix_commission(double client_fix_commission) {
		this.client_fix_commission = client_fix_commission;
	}

	public double getClient_fix_commission() {
		return client_fix_commission;
	}

	public void setClient_spread_commission(double client_spread_commission) {
		this.client_spread_commission = client_spread_commission;
	}

	public double getClient_spread_commission() {
		return client_spread_commission;
	}

	public void setRebate_commission_lot(double rebate_commission_lot) {
		this.rebate_commission_lot = rebate_commission_lot;
	}

	public double getRebate_commission_lot() {
		return rebate_commission_lot;
	}

	public void setRebate_commission_pip(double rebate_commission_pip) {
		this.rebate_commission_pip = rebate_commission_pip;
	}

	public double getRebate_commission_pip() {
		return rebate_commission_pip;
	}

	public void setTrade_swaps(double trade_swaps) {
		this.trade_swaps = trade_swaps;
	}

	public double getTrade_swaps() {
		return trade_swaps;
	}

	public void setTrade_pl(double trade_pl) {
		this.trade_pl = trade_pl;
	}

	public double getTrade_pl() {
		return trade_pl;
	}

	public void setOpen_trade_time(Date open_trade_time) {
		this.open_trade_time = open_trade_time;
	}

	public Date getOpen_trade_time() {
		return open_trade_time;
	}

	public void setClose_trade_time(Date close_trade_time) {
		this.close_trade_time = close_trade_time;
	}

	public Date getClose_trade_time() {
		return close_trade_time;
	}

	public void setRebate_code(String rebate_code) {
		this.rebate_code = rebate_code;
	}

	public String getRebate_code() {
		return rebate_code;
	}

	public void setRebate_type_lot(String rebate_type_lot) {
		this.rebate_type_lot = rebate_type_lot;
	}

	public String getRebate_type_lot() {
		return rebate_type_lot;
	}

	public void setRebate_per_lot(double rebate_per_lot) {
		this.rebate_per_lot = rebate_per_lot;
	}

	public double getRebate_per_lot() {
		return rebate_per_lot;
	}

	public void setRebate_type_pip(String rebate_type_pip) {
		this.rebate_type_pip = rebate_type_pip;
	}

	public String getRebate_type_pip() {
		return rebate_type_pip;
	}

	public void setRebate_per_pip(double rebate_per_pip) {
		this.rebate_per_pip = rebate_per_pip;
	}

	public double getRebate_per_pip() {
		return rebate_per_pip;
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
	

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (brand_code != null)
			builder.append("brand_code: " + brand_code + ", ");
		if (platform != null)
			builder.append("platform: " + platform + ", ");
		if (ticket != null)
			builder.append("ticket: " + ticket + ", ");
		if (ib_code != null)
			builder.append("ib_code: " + ib_code + ", ");
		if (client_code != null)
			builder.append("client_code: " + client_code + ", ");
		if (client_ib_code != null)
			builder.append("client_ib_code: " + client_ib_code + ", ");
		if (product_group != null)
			builder.append("product_group: " + product_group + ", ");
		if (trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if (symbol != null)
			builder.append("symbol: " + symbol + ", ");
		if (buy_sell != null)
			builder.append("buy_sell: " + buy_sell + ", ");
		builder.append("lot: " + lot + ", ");
		if (currency != null)
			builder.append("currency: " + currency + ", ");
		builder.append("deposit: " + deposit + ", ");
		builder.append("client_fix_commission: " + client_fix_commission + ", ");
		builder.append("client_spread_commission: " + client_spread_commission + ", ");
		builder.append("rebate_commission_lot: " + rebate_commission_lot + ", ");
		builder.append("rebate_commission_pip: " + rebate_commission_pip + ", ");
		builder.append("trade_swaps: " + trade_swaps + ", ");
		builder.append("trade_pl: " + trade_pl + ", ");
		if (open_trade_time != null)
			builder.append("open_trade_time: " + open_trade_time.toString() + ", ");
		if (close_trade_time != null)
			builder.append("close_trade_time: " + close_trade_time.toString() + ", ");
		if (rebate_code != null)
			builder.append("rebate_code: " + rebate_code + ", ");
		if (rebate_type_lot != null)
			builder.append("rebate_type_lot: " + rebate_type_lot + ", ");
		builder.append("rebate_per_lot: " + rebate_per_lot + ", ");
		if (rebate_type_pip != null)
			builder.append("rebate_type_pip: " + rebate_type_pip + ", ");
		builder.append("rebate_per_pip: " + rebate_per_pip + ", ");
		if (last_update_time != null)
			builder.append("last_update_time: " + last_update_time.toString() + ", ");
		if (last_update_user != null)
			builder.append("last_update_user: " + last_update_user + ", ");
		return builder.toString();
	}
}
