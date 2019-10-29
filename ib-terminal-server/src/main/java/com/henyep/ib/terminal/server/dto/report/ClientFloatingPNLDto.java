package com.henyep.ib.terminal.server.dto.report;

public class ClientFloatingPNLDto {

	private String account_number;
	private Double floating_pnl;
	
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public Double getFloating_pnl() {
		return floating_pnl;
	}
	public void setFloating_pnl(Double floating_pnl) {
		this.floating_pnl = floating_pnl;
	}	
}
