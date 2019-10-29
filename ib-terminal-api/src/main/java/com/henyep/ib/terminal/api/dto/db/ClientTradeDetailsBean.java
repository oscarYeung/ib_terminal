package com.henyep.ib.terminal.api.dto.db;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClientTradeDetailsBean {

	private String ticket;
	private String group_code;
	private String jurisdiction;
	private String spread_type;
	private Integer client_code;
	private String platform;
	private String bs;
	private String symbol;
	private Double pl;
	private Double commission;
	private Double lot;
	private Integer account_identify;
	private Double swap;
	private Double open_price;
	private Double close_price;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date trade_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date open_time;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date close_time;

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicket() {
		return ticket;
	}

	public String getGroup_code() {
		return group_code;
	}

	public void setGroup_code(String group_code) {
		this.group_code = group_code;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getSpread_type() {
		return spread_type;
	}

	public void setSpread_type(String spread_type) {
		this.spread_type = spread_type;
	}

	public void setClient_code(Integer client_code) {
		this.client_code = client_code;
	}

	public Integer getClient_code() {
		return client_code;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}

	public void setBs(String bs) {
		this.bs = bs;
	}

	public String getBs() {
		return bs;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setPl(Double pl) {
		this.pl = pl;
	}

	public Double getPl() {
		return pl;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getCommission() {
		return commission;
	}

	public void setSwap(Double swap) {
		this.swap = swap;
	}

	public Double getSwap() {
		return swap;
	}

	public void setOpen_price(Double open_price) {
		this.open_price = open_price;
	}

	public Double getOpen_price() {
		return open_price;
	}

	public void setClose_price(Double close_price) {
		this.close_price = close_price;
	}

	public Double getClose_price() {
		return close_price;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setOpen_time(Date open_time) {
		this.open_time = open_time;
	}

	public Date getOpen_time() {
		return open_time;
	}

	public void setClose_time(Date close_time) {
		this.close_time = close_time;
	}

	public Date getClose_time() {
		return close_time;
	}

	public Double getLot() {
		return lot;
	}

	public void setLot(Double lot) {
		this.lot = lot;
	}

	public Integer getAccount_identify() {
		return account_identify;
	}

	public void setAccount_identify(Integer account_identify) {
		this.account_identify = account_identify;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (ticket != null)
			builder.append("ticket: " + ticket + ", ");
		builder.append("client_code: " + client_code + ", ");
		if (platform != null)
			builder.append("platform: " + platform + ", ");
		if (bs != null)
			builder.append("bs: " + bs + ", ");
		builder.append("lot: " + lot + ", ");
		if (symbol != null)
			builder.append("symbol: " + symbol + ", ");
		builder.append("pl: " + pl + ", ");
		builder.append("commission: " + commission + ", ");
		builder.append("swap: " + swap + ", ");
		builder.append("account_identify: " + account_identify + ", ");
		builder.append("open_price: " + open_price + ", ");
		builder.append("close_price: " + close_price + ", ");
		if (trade_date != null)
			builder.append("trade_date: " + trade_date.toString() + ", ");
		if (open_time != null)
			builder.append("open_time: " + open_time.toString() + ", ");
		if (close_time != null)
			builder.append("close_time: " + close_time.toString() + ", ");
		return builder.toString();
	}
}
