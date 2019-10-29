package com.henyep.ib.terminal.server.dto.report;

import java.util.Date;

public class ClientMarginInOutReportDto {

	private String ib_code;
	private String client_code;
	private String client_name;
	private Date start_date;
	private Date end_date;
	private Double total_margin_in;
	private Double total_margin_out;
	private Double net_total_margin_in;
	private Double trade_pl;
	
	private Date trade_date;
	private Date last_trade_date;
	private Double trade_date_pl;
	private Double last_trade_date_pl;
	
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
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
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
	public Double getTotal_margin_in() {
		return total_margin_in;
	}
	public void setTotal_margin_in(Double total_margin_in) {
		this.total_margin_in = total_margin_in;
	}
	public Double getTotal_margin_out() {
		return total_margin_out;
	}
	public void setTotal_margin_out(Double total_margin_out) {
		this.total_margin_out = total_margin_out;
	}
	public Double getNet_total_margin_in() {
		return net_total_margin_in;
	}
	public void setNet_total_margin_in(Double net_total_margin_in) {
		this.net_total_margin_in = net_total_margin_in;
	}
	public Double getTrade_pl() {
		return trade_pl;
	}
	public void setTrade_pl(Double trade_pl) {
		this.trade_pl = trade_pl;
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
	public Double getTrade_date_pl() {
		return trade_date_pl;
	}
	public void setTrade_date_pl(Double trade_date_pl) {
		this.trade_date_pl = trade_date_pl;
	}
	public Double getLast_trade_date_pl() {
		return last_trade_date_pl;
	}
	public void setLast_trade_date_pl(Double last_trade_date_pl) {
		this.last_trade_date_pl = last_trade_date_pl;
	}
	
}
