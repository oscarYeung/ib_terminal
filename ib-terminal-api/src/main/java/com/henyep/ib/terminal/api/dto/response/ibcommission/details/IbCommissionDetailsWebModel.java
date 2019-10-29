package com.henyep.ib.terminal.api.dto.response.ibcommission.details;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.henyep.ib.terminal.api.global.Constants;

public class IbCommissionDetailsWebModel implements Serializable {

	private static final long serialVersionUID = 4246820090849407373L;

	private String brand_code;
	private String platform;
	private String ticket;
	private String client_code;
	private String ib_code;
	private String client_ib_code;
	private String product_group;
	private String symbol;
	private String buy_sell;
	private BigDecimal lot;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date trade_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date open_trade_time;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern=Constants.FORMAT_DATETIME)
	private Date close_trade_time;
	private BigDecimal trade_pl;
	private String currency;
	private BigDecimal client_fix_commission;
	private BigDecimal rebate_commission_pip;
	private BigDecimal rebate_commission_lot;

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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

	public String getIb_code() {
		return ib_code;
	}

	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
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

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getBuy_sell() {
		return buy_sell;
	}

	public void setBuy_sell(String buy_sell) {
		this.buy_sell = buy_sell;
	}

	public BigDecimal getLot() {
		return lot;
	}

	public void setLot(BigDecimal lot) {
		this.lot = lot;
	}

	public Date getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(Date trade_date) {
		this.trade_date = trade_date;
	}

	public Date getOpen_trade_time() {
		return open_trade_time;
	}

	public void setOpen_trade_time(Date open_trade_time) {
		this.open_trade_time = open_trade_time;
	}

	public Date getClose_trade_time() {
		return close_trade_time;
	}

	public void setClose_trade_time(Date close_trade_time) {
		this.close_trade_time = close_trade_time;
	}

	public BigDecimal getTrade_pl() {
		return trade_pl;
	}

	public void setTrade_pl(BigDecimal trade_pl) {
		this.trade_pl = trade_pl;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getClient_fix_commission() {
		return client_fix_commission;
	}

	public void setClient_fix_commission(BigDecimal client_fix_commission) {
		this.client_fix_commission = client_fix_commission;
	}

	public BigDecimal getRebate_commission_pip() {
		return rebate_commission_pip;
	}

	public void setRebate_commission_pip(BigDecimal rebate_commission_pip) {
		this.rebate_commission_pip = rebate_commission_pip;
	}

	public BigDecimal getRebate_commission_lot() {
		return rebate_commission_lot;
	}

	public void setRebate_commission_lot(BigDecimal rebate_commission_lot) {
		this.rebate_commission_lot = rebate_commission_lot;
	}

}
