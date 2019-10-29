package com.henyep.ib.terminal.api.dto.response.marginout;

import java.io.Serializable;

public class GetMaxWithdrawalResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5318656784580255786L;

	
	private String ib_code;
	private Double max_withdrawal;
	private Double pending_margin_out;
	private Double account_balance;	
	private Double subsequent_margin_out_fee;

	public Double getSubsequent_margin_out_fee() {
		return subsequent_margin_out_fee;
	}
	public void setSubsequent_margin_out_fee(Double subsequent_margin_out_fee) {
		this.subsequent_margin_out_fee = subsequent_margin_out_fee;
	}
	public String getIb_code() {
		return ib_code;
	}
	public void setIb_code(String ib_code) {
		this.ib_code = ib_code;
	}
	public Double getMax_withdrawal() {
		return max_withdrawal;
	}
	public void setMax_withdrawal(Double max_withdrawal) {
		this.max_withdrawal = max_withdrawal;
	}
	
	public Double getPending_margin_out() {
		return pending_margin_out;
	}
	public void setPending_margin_out(Double pending_margin_out) {
		this.pending_margin_out = pending_margin_out;
	}	
	
	public Double getAccount_balance() {
		return account_balance;
	}
	public void setAccount_balance(Double account_balance) {
		this.account_balance = account_balance;
	}
}
