package com.henyep.ib.terminal.api.dto.response.ibcommission;

import java.io.Serializable;

public class IbCommissionModel implements Serializable{
	private static final long serialVersionUID = -7768010240383400299L;

	private String ib_code;
	private String ib_name;
	private Double total_lot;
	private Double referral_total_lot;
	private Double net_margin_in;
	private Double pl;
	private Double referral_pl;
	private Double commission;
	private Double referral_commission;
	private Double rebate;
	private Double referral_rebate;
	
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
	public Double getTotal_lot() {
		return total_lot;
	}
	public void setTotal_lot(Double total_lot) {
		this.total_lot = total_lot;
	}
	public Double getReferral_total_lot() {
		return referral_total_lot;
	}
	public void setReferral_total_lot(Double referral_total_lot) {
		this.referral_total_lot = referral_total_lot;
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
	public Double getReferral_pl() {
		return referral_pl;
	}
	public void setReferral_pl(Double referral_pl) {
		this.referral_pl = referral_pl;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getReferral_commission() {
		return referral_commission;
	}
	public void setReferral_commission(Double referral_commission) {
		this.referral_commission = referral_commission;
	}
	public Double getRebate() {
		return rebate;
	}
	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
	public Double getReferral_rebate() {
		return referral_rebate;
	}
	public void setReferral_rebate(Double referral_rebate) {
		this.referral_rebate = referral_rebate;
	}
	
	
}
