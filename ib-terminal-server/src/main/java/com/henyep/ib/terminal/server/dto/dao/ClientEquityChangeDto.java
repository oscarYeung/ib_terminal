package com.henyep.ib.terminal.server.dto.dao;

import java.math.BigDecimal;

public class ClientEquityChangeDto {

	protected BigDecimal equity_change_1;
	protected BigDecimal equity_change_2;
	protected BigDecimal equity;
	protected BigDecimal previous_equity;
	protected BigDecimal floating;
	protected BigDecimal previous_floating;
	protected BigDecimal net_margin;
	protected BigDecimal net_pl;

	public BigDecimal getEquity_change_1() {
		return equity_change_1;
	}

	public void setEquity_change_1(BigDecimal equity_change_1) {
		this.equity_change_1 = equity_change_1;
	}

	public BigDecimal getEquity_change_2() {
		return equity_change_2;
	}

	public void setEquity_change_2(BigDecimal equity_change_2) {
		this.equity_change_2 = equity_change_2;
	}

	public BigDecimal getEquity() {
		return equity;
	}

	public void setEquity(BigDecimal equity) {
		this.equity = equity;
	}

	public BigDecimal getPrevious_equity() {
		return previous_equity;
	}

	public void setPrevious_equity(BigDecimal previous_equity) {
		this.previous_equity = previous_equity;
	}

	public BigDecimal getFloating() {
		return floating;
	}

	public void setFloating(BigDecimal floating) {
		this.floating = floating;
	}

	public BigDecimal getPrevious_floating() {
		return previous_floating;
	}

	public void setPrevious_floating(BigDecimal previous_floating) {
		this.previous_floating = previous_floating;
	}

	public BigDecimal getNet_margin() {
		return net_margin;
	}

	public void setNet_margin(BigDecimal net_margin) {
		this.net_margin = net_margin;
	}

	public BigDecimal getNet_pl() {
		return net_pl;
	}

	public void setNet_pl(BigDecimal net_pl) {
		this.net_pl = net_pl;
	}

}
