package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;

public class IbClientSummariesModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -142530929157816035L;
	
	private String ib_code;
	private String client_code;
	private Double total_lot;
	private Double net_margin_in;
	private Double pl;
	private Double commission;
	private Double rebate;
	
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
	public Double getTotal_lot() {
		return total_lot;
	}
	public void setTotal_lot(Double total_lot) {
		this.total_lot = total_lot;
	}
	public Double getNet_margin_in() {
		return net_margin_in;
	}
	public void setNet_margin_in(Double net_margin_in) {
		this.net_margin_in = net_margin_in;
	}
	public Double getPl() {
		return pl;
	}
	public void setPl(Double pl) {
		this.pl = pl;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getRebate() {
		return rebate;
	}
	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
}
