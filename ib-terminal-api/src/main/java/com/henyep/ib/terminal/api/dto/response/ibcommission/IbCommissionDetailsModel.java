package com.henyep.ib.terminal.api.dto.response.ibcommission;

public class IbCommissionDetailsModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2477912977810069919L;
	
	private String client_code;
	private double total_lot;
	private double pl;
	private double commission;
	private double rebate;
	private double net_margin_in;
	
	public double getTotal_lot() {
		return total_lot;
	}
	public void setTotal_lot(double total_lot) {
		this.total_lot = total_lot;
	}
	public double getPl() {
		return pl;
	}
	public void setPl(double pl) {
		this.pl = pl;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	public double getNet_margin_in() {
		return net_margin_in;
	}
	public void setNet_margin_in(double net_margin_in) {
		this.net_margin_in = net_margin_in;
	}
	public String getClient_code() {
		return client_code;
	}
	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}
	
}
