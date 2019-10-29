package com.henyep.ib.terminal.server.dto.report;

import java.util.Date;

public class IbTradeAmountReportDto {
	private String ib_code;
	private String  ib_name;
	private String  brand_code;
	private Date start_date;
	private Date end_date;
	private Double accumulated_lot;
	private Double freq_trading_count;
	private Double freq_trading_lot;
	private Double freq_trading_precentage;
	
	private Date trade_date;
	private Date last_trade_date;
	private Double trade_date_lot;
	private Double last_trade_date_lot;
	
	private Double lot_diff;
	
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
	public Double getAccumulated_lot() {
		return accumulated_lot;
	}
	public void setAccumulated_lot(Double accumulated_lot) {
		this.accumulated_lot = accumulated_lot;
	}
	public Double getFreq_trading_count() {
		return freq_trading_count;
	}
	public void setFreq_trading_count(Double freq_trading_count) {
		this.freq_trading_count = freq_trading_count;
	}
	public Double getFreq_trading_lot() {
		return freq_trading_lot;
	}
	public void setFreq_trading_lot(Double freq_trading_lot) {
		this.freq_trading_lot = freq_trading_lot;
	}
	public Double getFreq_trading_precentage() {
		return freq_trading_precentage;
	}
	public void setFreq_trading_precentage(Double freq_trading_precentage) {
		this.freq_trading_precentage = freq_trading_precentage;
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
	public Double getTrade_date_lot() {
		return trade_date_lot;
	}
	public void setTrade_date_lot(Double trade_date_lot) {
		this.trade_date_lot = trade_date_lot;
	}
	public Double getLast_trade_date_lot() {
		return last_trade_date_lot;
	}
	public void setLast_trade_date_lot(Double last_trade_date_lot) {
		this.last_trade_date_lot = last_trade_date_lot;
	}
	public Double getLot_diff() {
		return lot_diff;
	}
	public void setLot_diff(Double lot_diff) {
		this.lot_diff = lot_diff;
	}
	
}
